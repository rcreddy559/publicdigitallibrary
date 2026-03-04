package com.library.service;

import java.util.List;
import java.util.Map;

import com.library.dto.BookResponse;
import com.library.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Mono<BookResponse> createBook(Book book);

    Flux<BookResponse> getAllBooks();

    Mono<BookResponse> getBookById(Long id);

    Mono<BookResponse> updateBook(Long id, Book book);

    Mono<Void> deleteBook(Long id);

    Flux<BookResponse> sortBy(String sortBy, String order);

    public Mono<Map<String, List<BookResponse>>> getBooksByAuthor();

}
