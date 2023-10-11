package com.taboo.service;

import com.taboo.entity.User;
import com.taboo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User save(User user) {
        User existedUser = repository.findByTelegramId(user.getTelegramId());
        if (existedUser == null) {
            existedUser = repository.save(user);
        }
        return existedUser;
    }
}
