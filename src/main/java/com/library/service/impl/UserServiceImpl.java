package com.library.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.library.dto.UserSummary;
import com.library.exception.UserEmailAlreadyExistsException;
import com.library.exception.UserNotFoundException;
import com.library.model.LibraryUser;
import com.library.repository.UserRepository;
import com.library.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_ROLE = "READER";

    private final UserRepository userRepository;

    @Override
    public Mono<LibraryUser> createUser(LibraryUser user) {
        user.setId(null);
        user.setRole(normalizeRole(user.getRole()));
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<LibraryUser>error(new UserEmailAlreadyExistsException(user.getEmail())))
                .switchIfEmpty(userRepository.save(user));
    }

    @Override
    public Flux<LibraryUser> getAllUsers() {
        Mono<Long> justCurrentMono = Mono.just(System.currentTimeMillis());
        justCurrentMono.subscribe(time -> System.out.println("time1: " + time));

        justCurrentMono.subscribe(time -> System.out.println("time2: " + time));

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
                    return userRepository.findByEmail(user.getEmail())
                            .flatMap(byEmail -> {
                                if (!byEmail.getId().equals(existing.getId())) {
                                    return Mono.error(new UserEmailAlreadyExistsException(user.getEmail()));
                                }
                                return Mono.just(existing);
                            })
                            .switchIfEmpty(Mono.just(existing))
                            .flatMap(current -> {
                                current.setFullName(user.getFullName());
                                current.setEmail(user.getEmail());
                                current.setPhone(user.getPhone());
                                current.setActive(user.getActive());
                                if (user.getRole() == null || user.getRole().isBlank()) {
                                    current.setRole(normalizeRole(current.getRole()));
                                } else {
                                    current.setRole(normalizeRole(user.getRole()));
                                }
                                return userRepository.save(current);
                            });
                });
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return getUserById(id).flatMap(existing -> userRepository.deleteById(existing.getId()));
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return DEFAULT_USER_ROLE;
        }
        return role.trim().toUpperCase(Locale.ROOT);
    }

    @Override
    public Mono<UserSummary> getSummary(String role) {

        return Mono.empty();

    }

    public void activateAllUsers() {
        Flux<LibraryUser> users = userRepository.findAll();
        users.flatMap(user -> {
            if (user.getId() % 2 == 0) {
                user.setActive(false);
                user.setFullName(user.getFullName() + ": " + user.getActive());
            }

            return userRepository.save(user);
        }).subscribe(user -> {
            System.out.println("activateAllUsers: " + user.getFullName() + ": " + user.getActive());
        });

    }

    public Mono<Map<Boolean, List<LibraryUser>>> getAllUsersGroupedBy() {
        return userRepository.findAll()
                .collect(Collectors.groupingBy(LibraryUser::getActive));
    }

    public Mono<Map<String, Long>> getAllUsersActiveAndInActiveCount() {

        return userRepository.findAll().collect(Collectors.groupingBy(LibraryUser::getActive, Collectors.counting()))
                .map(res -> Map.of("Active", res.getOrDefault(true, 0L),
                        "Inactive", res.getOrDefault(false, 0L)));
    }

}
