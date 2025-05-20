package org.liemartt.filestorage.service;

import lombok.RequiredArgsConstructor;
import org.liemartt.filestorage.DTO.share.ShareItemRequestDTO;
import org.liemartt.filestorage.exception.InternalServerException;
import org.liemartt.filestorage.exception.UserNotFoundException;
import org.liemartt.filestorage.model.SharedItem;
import org.liemartt.filestorage.model.ShareRole;
import org.liemartt.filestorage.model.User;
import org.liemartt.filestorage.repository.SharedItemRepository;
import org.liemartt.filestorage.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareService {
    private final SharedItemRepository sharedItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public void shareItem(ShareItemRequestDTO request, User currentUser) {
        User sharedWithUser = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + request.getUsername()));

        // Check if item is already shared with this user
        sharedItemRepository.findByItemPathAndSharedWithUser(request.getItemPath(), sharedWithUser)
                .ifPresent(sharedItem -> {
                    throw new InternalServerException("Item is already shared with this user");
                });

        SharedItem sharedItem = new SharedItem();
        sharedItem.setItemPath(request.getItemPath());
        sharedItem.setSharedWithUser(sharedWithUser);
        sharedItem.setSharedByUser(currentUser);
        sharedItem.setRole(request.getRole());

        sharedItemRepository.save(sharedItem);
    }

    @Transactional
    public void unshareItem(String itemPath, String username, User currentUser) {
        User sharedWithUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + username));

        sharedItemRepository.deleteByItemPathAndSharedWithUser(itemPath, sharedWithUser);
    }

    public List<SharedItem> getSharedItems(User user) {
        return sharedItemRepository.findBySharedWithUser(user);
    }

    public boolean hasAccess(String itemPath, User user, ShareRole requiredRole) {
        return sharedItemRepository.findByItemPathAndSharedWithUser(itemPath, user)
                .map(sharedItem -> {
                    if (requiredRole == ShareRole.VIEWER) {
                        return true; // Both VIEWER and OWNER can view
                    }
                    return sharedItem.getRole() == ShareRole.OWNER;
                })
                .orElse(false);
    }
} 