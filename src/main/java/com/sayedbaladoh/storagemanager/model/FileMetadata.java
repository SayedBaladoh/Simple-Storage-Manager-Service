package com.sayedbaladoh.storagemanager.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class FileMetadata implements Serializable {
    private String id;
    private String fileName;
    private String filePath;
    private long retentionPeriodInMinutes;
    private Long uploadedAt; // Unix timestamp
    private Long expiredAt; // Unix timestamp
}
