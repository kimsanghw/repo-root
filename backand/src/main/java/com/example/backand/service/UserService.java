package com.example.backand.service;

import com.example.backand.config.entity.User;
import com.example.backand.dto.CreateUserRequest;
import com.example.backand.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User create(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setCity(request.getCity());
        return repository.save(user);
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }
}


