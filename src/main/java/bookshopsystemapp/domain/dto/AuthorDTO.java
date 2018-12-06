package bookshopsystemapp.domain.dto;

public class AuthorDTO {
    private String firstName;
    private String lastName;
    private Long numberBooks;



    public AuthorDTO() {
    }

    public AuthorDTO(String firstName, String lastName, Long numberBooks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberBooks = numberBooks;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getNumberBooks() {
        return this.numberBooks;
    }

    public void setNumberBooks(Long numberBooks) {
        this.numberBooks = numberBooks;
    }
}
