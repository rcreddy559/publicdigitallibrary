package com.library.service.impl;

import com.library.dto.BookResponse;
import com.library.mapper.BookMapper;
import org.springframework.stereotype.Service;

import com.library.exception.BookNotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.service.BookService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    /*
     * // repor
     * 
     */
    @Override
    public Mono<BookResponse> createBook(Book book) {
        book.setId(null);
        return bookRepository.save(book)
                .map(bookMapper::toBookResponse);
    }

    @Override
    public Flux<BookResponse> getAllBooks() {
        return bookRepository.findAll().map(bookMapper::toBookResponse);
    }

    @Override
    public Mono<BookResponse> getBookById(Long id) {
        return bookRepository.findById(id).map(bookMapper::toBookResponse)
                .switchIfEmpty(Mono.error(new BookNotFoundException(id)));
    }

    @Override
    public Mono<BookResponse> updateBook(Long id, Book book) {
        return bookRepository.findById(id)
                .flatMap(existing -> {
                    existing.setTitle(book.getTitle());
                    existing.setAuthor(book.getAuthor());
                    existing.setIsbn(book.getIsbn());
                    existing.setPublishedYear(book.getPublishedYear());
                    existing.setAvailable(book.getAvailable());
                    return bookRepository.save(existing).map(bookMapper::toBookResponse);
                });
    }

    @Override
    public Mono<Void> deleteBook(Long id) {
        return getBookById(id).flatMap(existing -> bookRepository.deleteById(existing.getId()));
    }
}
