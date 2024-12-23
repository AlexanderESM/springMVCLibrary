package itk.academy.orekhov.springmvclibrary.entity;
import itk.academy.orekhov.springmvclibrary.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AuthorTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testSaveAndFindAuthor() {
        // Создаем нового автора
        Author author = new Author();
        author.setName("John Doe");

        // Сохраняем автора в базе данных
        Author savedAuthor = authorRepository.save(author);

        // Проверяем, что автор успешно сохранен
        Optional<Author> foundAuthor = authorRepository.findById(savedAuthor.getId());
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testUpdateAuthor() {
        // Создаем и сохраняем автора
        Author author = new Author();
        author.setName("Jane Doe");
        Author savedAuthor = authorRepository.save(author);

        // Обновляем имя автора
        savedAuthor.setName("Jane Smith");
        Author updatedAuthor = authorRepository.save(savedAuthor);

        // Проверяем, что обновление прошло успешно
        Optional<Author> foundAuthor = authorRepository.findById(updatedAuthor.getId());
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getName()).isEqualTo("Jane Smith");
    }

    @Test
    public void testDeleteAuthor() {
        // Создаем и сохраняем автора
        Author author = new Author();
        author.setName("Mark Twain");
        Author savedAuthor = authorRepository.save(author);

        // Удаляем автора
        authorRepository.deleteById(savedAuthor.getId());

        // Проверяем, что автор удален
        Optional<Author> foundAuthor = authorRepository.findById(savedAuthor.getId());
        assertThat(foundAuthor).isNotPresent();
    }

}