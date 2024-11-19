package com.sayedbaladoh.storagemanager.controller;

import com.sayedbaladoh.storagemanager.dto.FileUploadRequestDTO;
import com.sayedbaladoh.storagemanager.model.FileMetadata;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "File", description = "The File Api")
@RequestMapping("/api/files")
public interface FileApi {

    @Operation(summary = "Upload a file", description = "Saves a file locally and stores its metadata in data store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileMetadata> handleFileUpload(@Parameter(
            description = "File upload request details",
            required = true,
            schema = @Schema(implementation = FileUploadRequestDTO.class))
                                                  @Valid
                                                  @ModelAttribute FileUploadRequestDTO uploadRequestDto);
}