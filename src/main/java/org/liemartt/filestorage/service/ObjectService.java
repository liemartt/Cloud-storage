package org.liemartt.filestorage.service;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.liemartt.filestorage.DTO.BreadcrumbsDTO;
import org.liemartt.filestorage.DTO.folder.FolderRequestDTO;
import org.liemartt.filestorage.exception.EmptyFolderException;
import org.liemartt.filestorage.exception.InternalServerException;
import org.liemartt.filestorage.util.MinioUtil;
import org.liemartt.filestorage.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public abstract class ObjectService {

    @Value("${minio.bucket}")
    protected String bucket;

    protected final MinioClient minioClient;

    @Autowired
    public ObjectService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    protected List<BreadcrumbsDTO> getAll(FolderRequestDTO requestDTO, boolean isFile) {
        List<BreadcrumbsDTO> objectList = new ArrayList<>();

        Iterable<Result<Item>> results = MinioUtil.getFolderObjects(minioClient, bucket, requestDTO.getPath());
        if (!results.iterator().hasNext()) {
            log.info("A non-existent path to the folder has been sent to the server");
            throw new EmptyFolderException();
        }
        results.forEach(itemResult -> {
            try {
                String object = PathUtil.getObjectWithoutFirstPrefix(itemResult.get().objectName());
                String objectName = Paths.get(object).getFileName().toString();
                if (!isFile) {
                    if (itemResult.get().isDir()) {
                        objectList.add(new BreadcrumbsDTO(objectName, object));
                    }
                } else {
                    if (!itemResult.get().isDir() && !object.isEmpty() && !object.endsWith("/")) {
                        objectList.add(new BreadcrumbsDTO(objectName, object));
                    }
                }
            } catch (Exception e) {
                log.error("Error accessing the minio while searching for users objects");
                throw new InternalServerException();
            }
        });
        log.info("The objects in the user's folder were successfully found");
        return objectList;
    }
}
