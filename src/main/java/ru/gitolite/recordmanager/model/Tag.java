package ru.gitolite.recordmanager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@NamedEntityGraph(
    name = "graph.Tag.books",
    attributeNodes = @NamedAttributeNode("books")
)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy="tags")
    @JsonManagedReference
    private Set<Book> books = new HashSet<Book>();

    public Tag() {
    }

    public Tag(String name, Set<Book> books) {
        this.name = name;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getTags().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getTags().remove(this);
    }

    @Override
    public String toString() {
        return getName() + " (id: " + getId() + ")";
    }
}
