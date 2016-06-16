package com.kiosk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.kiosk.domain.enumeration.CardStatus;

import com.kiosk.domain.enumeration.CardType;
import org.hibernate.validator.constraints.Email;

/**
 * A Card.
 */
@Entity
@Table(name = "card")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "number", length = 10, unique = true, nullable = false)
    private String number;

    @NotNull
    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @NotNull
    @Column(name = "owner_surname", nullable = false)
    private String ownerSurname;

    @NotNull
    @Column(name = "sms_number", nullable = false)
    private String smsNumber;

    @Email
    @Column(length = 100, name = "email")
    @Size(max = 100)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CardType type;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Card() {
        this.setStatus(CardStatus.blocked);
        this.setType(CardType.BRONZE);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
    }

    public String getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        if(card.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Card{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", ownerName='" + ownerName + "'" +
            ", ownerSurname='" + ownerSurname + "'" +
            ", smsNumber='" + smsNumber + "'" +
            ", email='" + email + "'" +
            ", status='" + status + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
