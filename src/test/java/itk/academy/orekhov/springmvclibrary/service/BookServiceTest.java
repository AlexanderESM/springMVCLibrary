package itk.academy.orekhov.springmvclibrary.service;

import itk.academy.orekhov.springmvclibrary.entity.Book;
import itk.academy.orekhov.springmvclibrary.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository; // Mocking the BookRepository dependency

    @InjectMocks
    private BookService bookService; // The service that we will be testing

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes the mocks before each test
    }

    @Test
    public void testGetAllBooks() {
        // Arrange
        PageRequest pageable = PageRequest.of(0, 10); // Creating a pageable request
        Book book = new Book(); // Creating a mock book entity
        book.setId(1L);
        book.setTitle("Test Book");
        Page<Book> booksPage = new PageImpl<>(List.of(book)); // Creating a page of books

        when(bookRepository.findAll(pageable)).thenReturn(booksPage); // Mocking repository method

        // Act
        Page<Book> result = bookService.getAllBooks(pageable); // Calling the service method

        // Assert
        assertNotNull(result); // Ensure that result is not null
        assertEquals(1, result.getTotalElements()); // Verify that the page has one book
        assertEquals("Test Book", result.getContent().get(0).getTitle()); // Check book title
    }

    @Test
    public void testGetBookById() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book(); // Creating a mock book entity
        book.setId(bookId);
        book.setTitle("Test Book");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book)); // Mocking repository method

        // Act
        Optional<Book> result = bookService.getBookById(bookId); // Calling the service method

        // Assert
        assertTrue(result.isPresent()); // Ensure that the result is present
        assertEquals("Test Book", result.get().getTitle()); // Verify that the title matches
    }

    @Test
    public void testSaveBook() {
        // Arrange
        Book book = new Book();
        book.setTitle("New Book");

        when(bookRepository.save(book)).thenReturn(book); // Mocking save operation

        // Act
        Book result = bookService.saveBook(book); // Calling the service method

        // Assert
        assertNotNull(result); // Ensure that the result is not null
        assertEquals("New Book", result.getTitle()); // Check if the book title is as expected
    }

    @Test
    public void testDeleteBook() {
        // Arrange
        Long bookId = 1L;

        doNothing().when(bookRepository).deleteById(bookId); // Mocking the delete operation

        // Act
        bookService.deleteBook(bookId); // Calling the service method

        // Assert
        verify(bookRepository, times(1)).deleteById(bookId); // Verify that deleteById was called once
    }
}
