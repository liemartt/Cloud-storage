package org.liemartt.filestorage.DTO.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadFolderRequestDTO extends FolderRequestDTO{
    private String nameFolder;

    public DownloadFolderRequestDTO(String nameFolder,String path) {
        super(path);
        this.nameFolder = nameFolder;
    }
}

