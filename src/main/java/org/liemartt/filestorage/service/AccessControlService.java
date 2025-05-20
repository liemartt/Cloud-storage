package org.liemartt.filestorage.service;

import lombok.RequiredArgsConstructor;
import org.liemartt.filestorage.model.ShareRole;
import org.liemartt.filestorage.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessControlService {
    private final ShareService shareService;

    public boolean canView(String itemPath, User user) {
        return shareService.hasAccess(itemPath, user, ShareRole.VIEWER);
    }

    public boolean canEdit(String itemPath, User user) {
        return shareService.hasAccess(itemPath, user, ShareRole.OWNER);
    }
} 