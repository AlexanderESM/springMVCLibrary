package itk.academy.orekhov.springmvclibrary.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // Геттер для id
    public Long getId() {
        return id;
    }

    // Сеттер для id
    public void setId(Long id) {
        this.id = id;
    }

    // Геттер для title
    public String getTitle() {
        return title;
    }

    // Сеттер для title
    public void setTitle(String title) {
        this.title = title;
    }

    // Геттер для author
    public Author getAuthor() {
        return author;
    }

    // Сеттер для author
    public void setAuthor(Author author) {
        this.author = author;
    }

    // Переопределение метода equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author);
    }

    // Переопределение метода hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, title, author);
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + (author != null ? author.toString() : "null") +
                '}';
    }
}
