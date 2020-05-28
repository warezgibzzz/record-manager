package ru.gitolite.recordmanager.commands;

import com.github.javafaker.Faker;
import org.hibernate.Hibernate;
import ru.gitolite.recordmanager.dao.*;
import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.model.*;
import ru.gitolite.recordmanager.service.StateManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeedAction implements Action {
    @Override
    @SuppressWarnings("unchecked")
    public void apply() throws InvalidArgumentException {
        Faker faker = new Faker();

        AuthorDao authorDao = new AuthorDao();
        BookDao bookDao = new BookDao();
        CategoryDao categoryDao = new CategoryDao();
        CountryDao countryDao = new CountryDao();
        TagDao tagDao = new TagDao();
        UniversityDao universityDao = new UniversityDao();

        List<Category> categories = new ArrayList<Category>();
        List<Country> countries = new ArrayList<Country>();
        List<Book> books = new ArrayList<Book>();

        for (int i = 0; i < 4; i++) {
            Category category = new Category();
            category.setName(faker.book().publisher());
            category.setActive(faker.random().nextBoolean());
            categoryDao.save(category);
            categories.add(category);
        }

        for (int i = 0; i < 10; i++) {
            Author author = new Author();
            author.setFirstName(faker.name().firstName());
            author.setLastName(faker.name().lastName());
            author.setBirthDate(faker.date().birthday());
            authorDao.save(author);

            Book book = new Book();
            book.setName(faker.book().title());
            book.setCategory(categories.get(faker.random().nextInt(0, categories.size() - 1)));
            book.setISBN(faker.code().isbn13());
            book.setAuthor(author);
            bookDao.save(book);
            books.add(book);

            Country country = new Country();
            country.setName(faker.country().name());
            country.setShortName(faker.country().countryCode2());
            countryDao.save(country);
            countries.add(country);
        }

        for (int i = 0; i < 3; i++) {
            Tag tag = new Tag();
            tag.setName(faker.book().genre());
            tagDao.save(tag);
            for (int j = 1; j <= 4; j++) {
                Hibernate.initialize(Tag.class);
                tag.addBook(books.get(faker.random().nextInt(0, books.size() - 1)));
            }
        }

        for (int i = 0; i < 20; i++) {
            University university = new University();
            university.setCountry(countries.get(faker.random().nextInt(0, countries.size() - 1)));
            university.setTitle(faker.university().name());
            universityDao.save(university);
        }

        File seedLock = new File("seed.lock");
        try {
            seedLock.createNewFile();
            StateManager.getState().replace("seeded", true);
            Map<String, Action> actionMap = (Map<String, Action>) StateManager.getState().get("actions");
            actionMap.remove("seed");
            StateManager.setActions(actionMap);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }

    @Override
    public String toString() {
        return "seed" ;
    }

    @Override
    public String getDescription() {
        return "Prefills database with random entities" ;
    }
}
