package bookshopsystemapp.domain.dto;

import java.time.LocalDate;

public class BookDTO {
    private String title;
    private LocalDate releaseDate;
    private Integer copies;

    public BookDTO() {
    }

    public BookDTO(String title, LocalDate releaseDate, Integer copies) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.copies = copies;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getCopies() {
        return this.copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }
}
