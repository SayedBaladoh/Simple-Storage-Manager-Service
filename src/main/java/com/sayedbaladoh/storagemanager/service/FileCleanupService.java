package com.sayedbaladoh.storagemanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileCleanupService {

    private final LeaderElectionService leaderElectionService;
    private final StorageService storageService;

    @Scheduled(fixedRateString = "${storage.delete-cycle-time-in-minutes}", timeUnit = TimeUnit.MINUTES)
    public void cleanExpiredFiles() {
        log.info("Running file cleanup task.");
        if (leaderElectionService.isCurrentInstanceLeader()) {
            storageService.deleteExpiredFiles();
        } else {
            log.info("Current instance is not the leader. Skipping cleanup.");
        }
    }
}
