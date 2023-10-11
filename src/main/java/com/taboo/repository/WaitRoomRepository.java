package com.taboo.repository;

import com.taboo.entity.WaitRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitRoomRepository extends JpaRepository<WaitRoom, Long> {

    WaitRoom findByHash(String hash);
}
