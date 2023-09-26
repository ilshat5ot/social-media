package ru.sadykov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sadykov.repository.UserRepository;

@RequiredArgsConstructor

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }
}
