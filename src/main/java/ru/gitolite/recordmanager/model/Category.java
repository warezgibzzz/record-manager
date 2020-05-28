package ru.gitolite.recordmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@NamedEntityGraph(
    name = "graph.Category.books",
    attributeNodes = @NamedAttributeNode("books")
)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private boolean active;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Set<Book> books = new HashSet<Book>();


    public Category() {
    }

    public Category(String name, boolean active, Set<Book> books) {
        this.name = name;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.setCategory(this);
    }

    @Override
    public String toString() {
        return getName() + " (id: " + getId() + ")";
    }
}
