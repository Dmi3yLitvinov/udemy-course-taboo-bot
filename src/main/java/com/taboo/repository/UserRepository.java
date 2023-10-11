package com.taboo.repository;

import com.taboo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByTelegramId(Long telegramId);
}
