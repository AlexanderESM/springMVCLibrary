package itk.academy.orekhov.springmvclibrary.entity;

import itk.academy.orekhov.springmvclibrary.repository.AuthorRepository;
import itk.academy.orekhov.springmvclibrary.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Используем аннотацию для тестирования JPA-репозиториев
public class BookTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testSaveAndFindBook() {
        // Создаем автора и сохраняем его
        Author author = new Author();
        author.setName("John Doe");
        Author savedAuthor = authorRepository.save(author);

        // Создаем книгу и связываем с автором
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAuthor(savedAuthor);

        // Сохраняем книгу в базе данных
        Book savedBook = bookRepository.save(book);

        // Проверяем, что книга успешно сохранена
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Sample Book");
        assertThat(foundBook.get().getAuthor().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testUpdateBook() {
        // Создаем автора и сохраняем его
        Author author = new Author();
        author.setName("Jane Doe");
        Author savedAuthor = authorRepository.save(author);

        // Создаем и сохраняем книгу
        Book book = new Book();
        book.setTitle("Old Title");
        book.setAuthor(savedAuthor);
        Book savedBook = bookRepository.save(book);

        // Обновляем заголовок книги
        savedBook.setTitle("Updated Title");
        Book updatedBook = bookRepository.save(savedBook);

        // Проверяем, что обновление прошло успешно
        Optional<Book> foundBook = bookRepository.findById(updatedBook.getId());
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void testDeleteBook() {
        // Создаем автора и сохраняем его
        Author author = new Author();
        author.setName("Mark Twain");
        Author savedAuthor = authorRepository.save(author);

        // Создаем и сохраняем книгу
        Book book = new Book();
        book.setTitle("To Be Deleted");
        book.setAuthor(savedAuthor);
        Book savedBook = bookRepository.save(book);

        // Удаляем книгу
        bookRepository.deleteById(savedBook.getId());

        // Проверяем, что книга удалена
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        assertThat(foundBook).isNotPresent();
    }

    @Test
    public void testFindBookByTitle() {
        // Создаем автора и сохраняем его
        Author author = new Author();
        author.setName("Charles Dickens");
        Author savedAuthor = authorRepository.save(author);

        // Создаем и сохраняем книгу
        Book book = new Book();
        book.setTitle("Great Expectations");
        book.setAuthor(savedAuthor);
        bookRepository.save(book);

        // Проверяем, что книга найдена по названию
        Optional<Book> foundBook = bookRepository.findByTitle("Great Expectations");
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getAuthor().getName()).isEqualTo("Charles Dickens");
    }
}
