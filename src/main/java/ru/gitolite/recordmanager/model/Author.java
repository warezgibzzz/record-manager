package ru.gitolite.recordmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@NamedEntityGraph(
    name = "graph.Author.books",
    attributeNodes = @NamedAttributeNode("books")
)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date birthDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Set<Book> books = new HashSet<Book>();


    public Author() {
    }

    public Author(String firstName, String lastName, Date birthDate, Set<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " (id: " + getId() + ")";
    }
}
