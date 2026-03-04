package com.library.repository;

import com.library.model.LibraryUser;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<LibraryUser, Long> {

    @Query("SELECT COUNT(*) FROM \"library_user\" WHERE \"active\" = true")
    Mono<Long> countActiveUsers();

    Mono<LibraryUser> findByEmail(String email);

    Mono<LibraryUser> findByRole(String role);
}
