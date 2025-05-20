package org.liemartt.filestorage.controller;

import com.google.common.net.HttpHeaders;
import org.liemartt.filestorage.DTO.folder.DownloadFolderRequestDTO;
import org.liemartt.filestorage.DTO.folder.RemoveFolderRequestDTO;
import org.liemartt.filestorage.DTO.folder.RenameFolderRequestDTO;
import org.liemartt.filestorage.DTO.folder.UploadFolderRequestDTO;
import org.liemartt.filestorage.exception.EmptyObjectNameException;
import org.liemartt.filestorage.security.CustomUserDetails;
import org.liemartt.filestorage.service.FolderService;
import org.liemartt.filestorage.util.MinioUtil;
import org.liemartt.filestorage.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.liemartt.filestorage.util.PathUtil.getFullPath;
import static org.liemartt.filestorage.util.PathUtil.getPathToFolder;


@Controller
@RequestMapping("/folder")
public class FolderController {
    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping("/upload")
    public String uploadFolder(@ModelAttribute("folder") UploadFolderRequestDTO requestDTO,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        String encodedFolderPath = URLEncoder.encode(requestDTO.getPath(), StandardCharsets.UTF_8);
        String fullPath = getFullPath(userDetails.getId(), requestDTO.getPath());
        requestDTO.setPath(fullPath);

        folderService.upload(requestDTO);
        return "redirect:/home?path=" + encodedFolderPath;
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFolder(@ModelAttribute("folder") DownloadFolderRequestDTO requestDTO,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        String fullPath = getFullPath(userDetails.getId(), requestDTO.getPath());
        requestDTO.setPath(fullPath);

        ByteArrayResource fileData = folderService.download(requestDTO);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + URLEncoder.encode(requestDTO.getNameFolder(), StandardCharsets.UTF_8) + ".zip\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }

    @PostMapping("/remove")
    public String removeFolder(@ModelAttribute("folder") RemoveFolderRequestDTO requestDTO,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        String pathToFolder = getPathToFolder(requestDTO.getPath(), requestDTO.getNameFolder());
        String fullPath = getFullPath(userDetails.getId(), requestDTO.getPath());
        requestDTO.setPath(fullPath);

        folderService.remove(requestDTO);

        return "redirect:/home?path=" + pathToFolder;
    }

    @PostMapping("/rename")
    public String renameFolder(@ModelAttribute("folder") RenameFolderRequestDTO requestDTO,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (requestDTO.getNewPath().isEmpty()) {
            throw new EmptyObjectNameException();
        }

        String pathToFolder = getPathToFolder(requestDTO.getPath(), requestDTO.getNameFolder());
        String fullPath = getFullPath(userDetails.getId(), requestDTO.getPath());
        String newFullPath = fullPath.replace(requestDTO.getNameFolder(), requestDTO.getNewPath());
        requestDTO.setPath(fullPath);
        requestDTO.setNewPath(newFullPath);

        folderService.rename(requestDTO);

        return "redirect:/home?path=" + pathToFolder;
    }
}
