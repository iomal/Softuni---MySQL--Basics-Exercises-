package bookshopsystemapp.service;

import org.springframework.data.jpa.repository.Query;

import javax.persistence.OrderBy;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    void getAllAuthorsNamesByNumberBooks();

    void getAllBooksByAuthor(String author);

    void updateBook();
}
