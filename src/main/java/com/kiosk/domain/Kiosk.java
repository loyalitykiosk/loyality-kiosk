package com.kiosk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Kiosk.
 */
@Entity
@Table(name = "kiosk")
public class Kiosk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Size(min = 20, max = 20)
    @Column(name = "license", length = 20, nullable = false)
    private String license;

    @ManyToOne
    private User customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User user) {
        this.customer = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kiosk kiosk = (Kiosk) o;
        if(kiosk.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, kiosk.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Kiosk{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", location='" + location + "'" +
            ", license='" + license + "'" +
            '}';
    }
}
