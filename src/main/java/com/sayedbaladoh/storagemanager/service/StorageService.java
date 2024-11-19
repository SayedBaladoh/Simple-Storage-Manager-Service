package com.sayedbaladoh.storagemanager.service;

import com.sayedbaladoh.storagemanager.dto.FileUploadRequestDTO;
import com.sayedbaladoh.storagemanager.model.FileMetadata;

public interface StorageService {

    FileMetadata store(FileUploadRequestDTO uploadRequestDto);

    void deleteExpiredFiles();
}
