package com.sayedbaladoh.storagemanager.service;

import com.sayedbaladoh.storagemanager.config.StorageProperties;
import com.sayedbaladoh.storagemanager.dto.FileUploadRequestDTO;
import com.sayedbaladoh.storagemanager.exception.StorageException;
import com.sayedbaladoh.storagemanager.model.FileMetadata;
import com.sayedbaladoh.storagemanager.repository.FileMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService implements StorageService {

    private final StorageProperties storageProperties;
    private final FileMetadataRepository fileMetadataRepository;

    public FileStorageService(StorageProperties storageProperties, FileMetadataRepository fileMetadataRepository) {
        this.storageProperties = storageProperties;
        this.fileMetadataRepository = fileMetadataRepository;

        createStorageDirectoryIfNotExist();
    }

    @Override
    public FileMetadata store(FileUploadRequestDTO uploadRequestDto) {
        try {
            if (uploadRequestDto.getFile().isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            File uploadedFile = saveFile(uploadRequestDto.getFile());
            FileMetadata storedFileMetadata = saveFileMetadata(uploadedFile, uploadRequestDto.getRetentionPeriodInMinutes());

            return storedFileMetadata;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }

    }

    @Override
    public void deleteExpiredFiles() {
        log.info("Deleting expired files.");

        long now = Instant.now().getEpochSecond();

        fileMetadataRepository.getAll()
                .forEach((fileId, fileMetadata) -> {
                    if (fileMetadata.getExpiredAt() <= now) {
                        deleteFile(fileMetadata);
                    }
                });
    }

    private File saveFile(MultipartFile multipartFile) throws IOException {
        String filePath = storageProperties.getPath() + File.separator + multipartFile.getOriginalFilename();
        File file = new File(filePath);
        multipartFile.transferTo(file.toPath());
        return file;
    }

    private FileMetadata saveFileMetadata(File file, long retentionPeriodInMinutes) {
        long expiredAt = Instant.now().plus(retentionPeriodInMinutes, ChronoUnit.MINUTES).getEpochSecond();

        FileMetadata storedFile = FileMetadata.builder()
                .id(UUID.randomUUID().toString())
                .fileName(file.getName())
                .filePath(file.getPath())
                .retentionPeriodInMinutes(retentionPeriodInMinutes)
                .uploadedAt(Instant.now().getEpochSecond())
                .expiredAt(expiredAt)
                .build();
        fileMetadataRepository.save(storedFile);

        return storedFile;
    }

    private void deleteFile(FileMetadata fileMetadata) {
        File file = new File(fileMetadata.getFilePath());
        if (file.exists() && file.delete()) {
            log.info("Expired file deleted. Id: {}, path:{} ", fileMetadata.getId(), fileMetadata.getFilePath());
        }
        fileMetadataRepository.delete(fileMetadata.getId());
    }

    private void createStorageDirectoryIfNotExist() {
        File storageDir = new File(storageProperties.getPath());
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
    }
}
