package bookshopsystemapp.repository;

import bookshopsystemapp.domain.dto.AuthorDTO;
import bookshopsystemapp.domain.dto.BookDTO;
import bookshopsystemapp.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);


    @Query(value = "SELECT new bookshopsystemapp.domain.dto.AuthorDTO(a.firstName, a.lastName, count(b.id))" +
            "FROM bookshopsystemapp.domain.entities.Book as b left join bookshopsystemapp.domain.entities.Author AS a " +
            "on a.id=b.author.id group by a.id order by count(b.id)")
    List<AuthorDTO> getAllAuthorsByNumberBooks();

    @Query(value = "SELECT new bookshopsystemapp.domain.dto.BookDTO(b.title, b.releaseDate, b.copies)" +
            "FROM bookshopsystemapp.domain.entities.Book as b left join bookshopsystemapp.domain.entities.Author AS a " +
            "on a.id=b.author.id where concat(a.firstName,' ',a.lastName)=:fullName order by b.releaseDate desc,b.title ")
    List<BookDTO> getAllBooksByAuthor(@Param(value = "fullName")String authorName);
}
