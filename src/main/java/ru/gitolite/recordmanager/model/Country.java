package ru.gitolite.recordmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
@NamedEntityGraph(
    name = "graph.Country.universities",
    attributeNodes = @NamedAttributeNode("universities")
)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String shortName;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @JsonBackReference
    private Set<University> universities = new HashSet<University>();

    public Country() {
    }

    public Country(String name, String shortName, Set<University> universities) {
        this.name = name;
        this.shortName = shortName;
        this.universities = universities;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Set<University> getUniversities() {
        return universities;
    }

    public void addUniversity(University university) {
        this.universities.add(university);
        university.setCountry(this);
    }

    @Override
    public String toString() {
        return getName() + " (id: " + getId() + ")";
    }
}
