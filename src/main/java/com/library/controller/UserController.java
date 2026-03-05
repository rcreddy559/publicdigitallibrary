package com.library.controller;

import com.library.model.LibraryUser;
import com.library.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.library.dto.UserSummary;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LibraryUser> create(@RequestBody LibraryUser user) {
        return userService.createUser(user);
    }

    @GetMapping
    public Flux<LibraryUser> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<LibraryUser> getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public Mono<LibraryUser> update(@PathVariable Long id, @RequestBody LibraryUser user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/summary")
    public Mono<UserSummary> getSummary(String role) {
        return userService.getSummary(role);
    }

    @GetMapping("/groupbyactive")
    public Mono<Map<Boolean, List<LibraryUser>>> groupByActive() {
        return userService.getAllUsersGroupedBy();
    }

    @GetMapping("/activecount")
    public Mono<Map<String, Long>> getAllUsersActiveAndInActiveCount() {
        return userService.getAllUsersActiveAndInActiveCount();
    }
}
