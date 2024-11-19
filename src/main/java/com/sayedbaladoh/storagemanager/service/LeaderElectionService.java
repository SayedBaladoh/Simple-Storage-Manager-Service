package com.sayedbaladoh.storagemanager.service;

import com.sayedbaladoh.storagemanager.repository.LeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaderElectionService {

    private static final String INSTANCE_ID = "instance-" + System.currentTimeMillis();

    private final LeaderRepository leaderRepository;

    public boolean isCurrentInstanceLeader() {
        return isCurrentLeader() || tryToBecomeLeader();
    }

    private boolean tryToBecomeLeader() {
        log.info("Trying {} to become a leader.", INSTANCE_ID);
        return leaderRepository.setLeader(INSTANCE_ID);
    }

    private boolean isCurrentLeader() {
        var isLeader = INSTANCE_ID.equals(leaderRepository.getLeader());
        log.info("Is current {} leader: {}.", INSTANCE_ID, isLeader);
        return isLeader;
    }
}
