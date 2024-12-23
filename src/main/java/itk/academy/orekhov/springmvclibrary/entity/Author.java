package itk.academy.orekhov.springmvclibrary.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
