package com.sayedbaladoh.storagemanager.controller;

import com.sayedbaladoh.storagemanager.dto.FileUploadRequestDTO;
import com.sayedbaladoh.storagemanager.model.FileMetadata;
import com.sayedbaladoh.storagemanager.service.StorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileController implements FileApi {

    private final StorageService storageService;

    @Override
    public ResponseEntity<FileMetadata> handleFileUpload(@Valid @ModelAttribute FileUploadRequestDTO uploadRequestDto) {
        FileMetadata fileMetadata = storageService.store(uploadRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(fileMetadata);
    }
}
