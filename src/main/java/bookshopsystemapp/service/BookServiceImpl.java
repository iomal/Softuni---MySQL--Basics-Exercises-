package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.*;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.hibernate.annotations.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final static String BOOKS_FILE_PATH = "G:\\1. MyStuff\\rabotni\\Softuni\\DB\\Exercises-Hibernate&Spring" +
            "\\06. DB-Advanced-Spring-Data-Intro-Exercises\\SpringDataIntro-master\\SpringDataIntro-master\\" +
            "Softuni---MySQL--Basics-Exercises-\\src\\main\\resources\\files\\books.txt";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository
            categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] booksFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);
        for (String line : booksFileContent) {
            String[] lineParams = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < lineParams.length; i++) {
                title.append(lineParams[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> getAllBooksTitlesAfter() {
        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"));

        return books.stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllAuthorsWithBookBefore() {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse("1990-01-01"));

        return books.stream().map(b -> String.format("%s %s", b.getAuthor().getFirstName(), b.getAuthor()
                .getLastName())).collect(Collectors.toSet());
    }

    @Override
    public void getAllAuthorsNamesByNumberBooks() {

        this.bookRepository.getAllAuthorsByNumberBooks().stream().forEach(author -> System.out.println(String.format("%s %s %d",
                author.getFirstName(), author.getLastName(), author.getNumberBooks())));
    }

    @Override
    public void getAllBooksByAuthor(String author) {
        this.bookRepository.getAllBooksByAuthor(author).stream().forEach(x ->
                System.out.println(String.format("%s %s %d", x.getTitle(), x.getReleaseDate().toString(), x.getCopies())));
    }

    @Override
    @Transactional( propagation=Propagation.REQUIRES_NEW)
    public void updateBook() {
        Book booktoUpdate=this.bookRepository.getOne(1);
        System.out.println(booktoUpdate.getAuthor());
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt((int) (this.authorRepository.count())) + 1;
        Author authorRandom = this.authorRepository.findById(randomId).orElse(null);
        assert authorRandom != null;
        this.authorRepository.saveAndFlush(authorRandom);
        return authorRandom;
    }

    private Set<Category> getRandomCategories() {
        Map<String, Category> categoryDict = new HashMap<>();
        Random random = new Random();
        int length = random.nextInt(5) + 1;
        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();
            if (!categoryDict.containsKey(category.getName()))
                categoryDict.put(category.getName(), category);
        }
        return new HashSet<>(categoryDict.values());
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count())) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }
}
