package itk.academy.orekhov.springmvclibrary.controller;

import itk.academy.orekhov.springmvclibrary.entity.Book;
import itk.academy.orekhov.springmvclibrary.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testGetAllBooks() throws Exception {
        // Настройка MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        // Создание тестовых данных
        Book book1 = new Book();
        book1.setTitle("Book One");

        Book book2 = new Book();
        book2.setTitle("Book Two");

        Page<Book> books = new PageImpl<>(Arrays.asList(book1, book2));

        // Мокирование поведения сервиса
        when(bookService.getAllBooks(any(PageRequest.class))).thenReturn(books);

        // Выполнение запроса и проверка результата
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Book One"))
                .andExpect(jsonPath("$.content[1].title").value("Book Two"));
    }

    @Test
    public void testGetBookById() throws Exception {
        // Настройка MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        // Создание тестовых данных
        Book book = new Book();
        book.setTitle("Test Book");

        // Мокирование поведения сервиса
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        // Выполнение запроса и проверка результата
        mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    public void testCreateBook() throws Exception {
        // Настройка MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        // Создание тестовых данных
        Book book = new Book();
        book.setTitle("New Book");

        // Мокирование поведения сервиса
        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        // Выполнение запроса и проверка результата
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Book\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Настройка MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        // Создание тестовых данных
        Book existingBook = new Book();
        existingBook.setTitle("Old Title");

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");

        // Мокирование поведения сервиса
        when(bookService.getBookById(1L)).thenReturn(Optional.of(existingBook));
        when(bookService.saveBook(any(Book.class))).thenReturn(updatedBook);

        // Выполнение запроса и проверка результата
        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Настройка MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        // Мокирование поведения сервиса
        when(bookService.getBookById(1L)).thenReturn(Optional.of(new Book()));

        // Выполнение запроса и проверка результата
        mockMvc.perform(delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
