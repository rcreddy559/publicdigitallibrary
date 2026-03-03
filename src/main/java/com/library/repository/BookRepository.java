package com.library.repository;

import com.library.model.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    @Query("SELECT COUNT(*) FROM book WHERE available = true")
    Mono<Long> countAvailableBooks();
}
