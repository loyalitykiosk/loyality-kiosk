package com.kiosk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Subscription.
 */
@Entity
@Table(name = "subscription")
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "sms_plan", nullable = false)
    private Integer smsPlan;

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

    public Integer getSmsPlan() {
        return smsPlan;
    }

    public void setSmsPlan(Integer smsPlan) {
        this.smsPlan = smsPlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subscription subscription = (Subscription) o;
        if(subscription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subscription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Subscription{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", smsPlan='" + smsPlan + "'" +
            '}';
    }
}
