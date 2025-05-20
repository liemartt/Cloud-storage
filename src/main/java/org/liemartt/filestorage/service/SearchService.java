package org.liemartt.filestorage.service;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.liemartt.filestorage.DTO.BreadcrumbsDTO;
import org.liemartt.filestorage.exception.SearchFileError;
import org.liemartt.filestorage.util.MinioUtil;
import org.liemartt.filestorage.util.PathUtil;
import org.springframework.stereotype.Service;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.liemartt.filestorage.util.PathUtil.getPathToFile;

@Service
@Slf4j
public class SearchService extends ObjectService {

    public SearchService(MinioClient minioClient) {
        super(minioClient);
    }

    public List<BreadcrumbsDTO> searchObject(String query, String path) {
        List<BreadcrumbsDTO> foundedObjects = new ArrayList<>();

        Iterable<Result<Item>> results = MinioUtil.getAllFolderObjects(minioClient, bucket, path);
        results.forEach(itemResult -> {
            try {
                String object = PathUtil.getObjectWithoutFirstPrefix(itemResult.get().objectName());
                String objectName = Paths.get(object).getFileName().toString();
                String pathToFile = getPathToFile(object, objectName);
                if (objectName.contains(query)) {
                    foundedObjects.add(new BreadcrumbsDTO(objectName, pathToFile));
                }
                log.info("The files on the request {} were successfully found", query);
            } catch (Exception e) {
                log.warn("Error with minio during file search");
                throw new SearchFileError();
            }

        });

        return foundedObjects;
    }

}
