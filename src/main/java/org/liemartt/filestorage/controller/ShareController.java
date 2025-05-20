package org.liemartt.filestorage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.liemartt.filestorage.DTO.share.ShareItemRequestDTO;
import org.liemartt.filestorage.model.SharedItem;
import org.liemartt.filestorage.model.User;
import org.liemartt.filestorage.security.CustomUserDetails;
import org.liemartt.filestorage.service.ShareService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/share")
@RequiredArgsConstructor
public class ShareController {
    private final ShareService shareService;

    @PostMapping
    public ResponseEntity<Void> shareItem(
            @Valid @RequestBody ShareItemRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        shareService.shareItem(request, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{itemPath}")
    public ResponseEntity<Void> unshareItem(
            @PathVariable String itemPath,
            @RequestParam String username,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        shareService.unshareItem(itemPath, username, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SharedItem>> getSharedItems(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(shareService.getSharedItems(userDetails.getUser()));
    }
} 