package org.liemartt.filestorage.repository;

import org.liemartt.filestorage.model.SharedItem;
import org.liemartt.filestorage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharedItemRepository extends JpaRepository<SharedItem, Long> {
    List<SharedItem> findBySharedWithUser(User user);
    
    Optional<SharedItem> findByItemPathAndSharedWithUser(String itemPath, User user);
    
    List<SharedItem> findByItemPath(String itemPath);
    
    void deleteByItemPathAndSharedWithUser(String itemPath, User user);
} 