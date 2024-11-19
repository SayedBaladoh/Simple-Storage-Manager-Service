package com.sayedbaladoh.storagemanager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class FileUploadRequestDTO {

    @NotNull
    private MultipartFile file;

    @Positive
    private Integer retentionPeriodInMinutes;
}
