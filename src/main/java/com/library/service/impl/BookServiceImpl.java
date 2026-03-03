package com.library.service.impl;

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

    /*
     * // repor
     * 
     */
    @Override
    public Mono<Book> createBook(Book book) {
        book.setId(null);
        return bookRepository.save(book);
    }

    @Override
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> getBookById(Long id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFoundException(id)));
    }

    @Override
    public Mono<Book> updateBook(Long id, Book book) {
        return getBookById(id)
                .flatMap(existing -> {
                    existing.setTitle(book.getTitle());
                    existing.setAuthor(book.getAuthor());
                    existing.setIsbn(book.getIsbn());
                    existing.setPublishedYear(book.getPublishedYear());
                    existing.setAvailable(book.getAvailable());
                    return bookRepository.save(existing);
                });
    }

    @Override
    public Mono<Void> deleteBook(Long id) {
        return getBookById(id).flatMap(existing -> bookRepository.deleteById(existing.getId()));
    }
}
