package com.library.service.impl;

import java.time.Duration;

import com.library.dto.BookResponse;
import com.library.mapper.BookMapper;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import com.library.exception.BookNotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.service.BookService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private static final Set<String> ALLOWED_FIELDS = Set.of("title", "author", "publishedYear").stream()
            .map(String::toLowerCase).collect(Collectors.toSet());

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

    @Override
    public Flux<BookResponse> sortBy(String sortBy, String order) {
        if (!ALLOWED_FIELDS.contains(sortBy.toLowerCase())) {
            return Flux.error(new IllegalArgumentException("Invalid sort field: " + sortBy));
        }

        Comparator<Book> comparator;

        switch (sortBy.toLowerCase()) {
            case "title" -> comparator = Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER);
            case "author" -> comparator = Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
            case "publishedyear" -> comparator = Comparator.comparingInt(Book::getPublishedYear);
            case "available" -> comparator = Comparator.comparing(Book::getAvailable);
            default -> comparator = Comparator.comparing(Book::getId);
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return bookRepository.findAll().sort(comparator).map(bookMapper::toBookResponse);
    }

    @Override
    public Mono<Map<String, List<BookResponse>>> getBooksByAuthor() {

        return bookRepository.findAll().map(bookMapper::toBookResponse)
                .collect(Collectors.groupingBy(BookResponse::getAuthor));
    }

}
