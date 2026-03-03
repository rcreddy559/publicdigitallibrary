package com.library.service.impl;

import com.library.exception.UserNotFoundException;
import com.library.model.LibraryUser;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<LibraryUser> createUser(LibraryUser user) {
        user.setId(null);
        return userRepository.save(user);
    }

    @Override
    public Flux<LibraryUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<LibraryUser> getUserById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }

    @Override
    public Mono<LibraryUser> updateUser(Long id, LibraryUser user) {
        return getUserById(id)
                .flatMap(existing -> {
                    existing.setFullName(user.getFullName());
                    existing.setEmail(user.getEmail());
                    existing.setPhone(user.getPhone());
                    existing.setActive(user.getActive());
                    return userRepository.save(existing);
                });
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return getUserById(id).flatMap(existing -> userRepository.deleteById(existing.getId()));
    }
}
