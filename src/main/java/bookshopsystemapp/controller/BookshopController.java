package bookshopsystemapp.controller;

import bookshopsystemapp.domain.entities.Category;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService, CategoryRepository categoryRepository) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
        Category category = this.categoryRepository.findById(1).orElse(null);
        System.out.println(category);
        System.out.println(category.hashCode());
        Category category2=this.categoryRepository.findById(1).orElse(null);
        System.out.println(category2);
        System.out.println(category2.hashCode());
        this.bookService.seedBooks();
//        this.bookTitles();
//        this.authorNames();
//        this.bookService.getAllAuthorsNamesByNumberBooks();
//        this.bookService.getAllBooksByAuthor("George Powell");
        System.out.println();
        this.bookService.updateBook();
    }

    private void bookTitles() {
        List<String> bookTitles = this.bookService.getAllBooksTitlesAfter();

        System.out.println(String.join("\r\n", bookTitles));
    }

    private void authorNames() {
        this.bookService.getAllAuthorsWithBookBefore().stream().forEach(a -> System.out.println(a));
    }


}
