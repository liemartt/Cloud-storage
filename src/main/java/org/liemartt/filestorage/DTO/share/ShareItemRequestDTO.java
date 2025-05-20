package org.liemartt.filestorage.DTO.share;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.liemartt.filestorage.model.ShareRole;

@Data
public class ShareItemRequestDTO {
    @NotBlank(message = "Item path is required")
    private String itemPath;

    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "Role is required")
    private ShareRole role;
} 