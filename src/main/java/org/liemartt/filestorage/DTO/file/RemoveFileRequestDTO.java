package org.liemartt.filestorage.DTO.file;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveFileRequestDTO extends FileRequestDTO {
    private String nameFile;

    public RemoveFileRequestDTO(String nameFile, String path) {
        super(path);
        this.nameFile = nameFile;
    }
}

