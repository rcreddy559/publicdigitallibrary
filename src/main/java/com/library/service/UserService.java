package com.library.service;

import com.library.dto.UserSummary;
import com.library.model.LibraryUser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface UserService {

    Mono<LibraryUser> createUser(LibraryUser user);

    Flux<LibraryUser> getAllUsers();

    Mono<LibraryUser> getUserById(Long id);

    Mono<LibraryUser> updateUser(Long id, LibraryUser user);

    Mono<Void> deleteUser(Long id);

    Mono<UserSummary> getSummary(String role);

    Mono<Map<String, List<LibraryUser>>> getAllUsersGroupedBy();

    Mono<Map<String, Long>> getAllUsersActiveAndInActiveCount();
}
