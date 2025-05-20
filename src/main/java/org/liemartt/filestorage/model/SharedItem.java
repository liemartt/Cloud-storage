package org.liemartt.filestorage.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shared_items")
public class SharedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_path", nullable = false)
    private String itemPath;

    @ManyToOne
    @JoinColumn(name = "shared_with_user_id", nullable = false)
    private User sharedWithUser;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShareRole role;

    @ManyToOne
    @JoinColumn(name = "shared_by_user_id", nullable = false)
    private User sharedByUser;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
} 