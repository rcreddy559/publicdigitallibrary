package com.library.service;

import com.library.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Mono<Book> createBook(Book book);

    Flux<Book> getAllBooks();

    Mono<Book> getBookById(Long id);

    Mono<Book> updateBook(Long id, Book book);

    Mono<Void> deleteBook(Long id);
}
