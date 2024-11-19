package com.sayedbaladoh.storagemanager.repository;

import com.sayedbaladoh.storagemanager.model.FileMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Slf4j
public class FileMetadataRepository {

    private static final String FILES_KEY = "files";

    private final HashOperations<String, String, FileMetadata> hashOperations;

    public FileMetadataRepository(RedisTemplate redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(FileMetadata fileMetadata) {
        hashOperations.put(FILES_KEY, fileMetadata.getId(), fileMetadata);
        log.info(String.format("FileMetadata with Id %s saved.", fileMetadata.getId()));
    }

    public FileMetadata get(String fileId) {
        return hashOperations.get(FILES_KEY, fileId);
    }

    public Map<String, FileMetadata> getAll() {
        return hashOperations.entries(FILES_KEY);
    }

    public void delete(String fileId) {
        hashOperations.delete(FILES_KEY, fileId);
        log.info(String.format("FileMetadata with Id %s deleted.", fileId));

    }

    public void update(FileMetadata fileMetadata) {
        hashOperations.put(FILES_KEY, fileMetadata.getId(), fileMetadata);
        log.info(String.format("FileMetadata with Id %s updated.", fileMetadata.getId()));
    }
}
