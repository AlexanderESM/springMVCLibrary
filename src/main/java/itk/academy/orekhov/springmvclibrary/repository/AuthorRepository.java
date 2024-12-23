package itk.academy.orekhov.springmvclibrary.repository;

import itk.academy.orekhov.springmvclibrary.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
