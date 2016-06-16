package com.kiosk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CardRequest.
 */
@Entity
@Table(name = "card_request")
public class CardRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @NotNull
    @Column(name = "owner_surname", nullable = false)
    private String ownerSurname;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "sms_number", nullable = false)
    private String smsNumber;

    @ManyToOne
    private Kiosk kiosk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }

    public Kiosk getKiosk() {
        return kiosk;
    }

    public void setKiosk(Kiosk kiosk) {
        this.kiosk = kiosk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CardRequest cardRequest = (CardRequest) o;
        if(cardRequest.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cardRequest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CardRequest{" +
            "id=" + id +
            ", ownerName='" + ownerName + "'" +
            ", ownerSurname='" + ownerSurname + "'" +
            ", email='" + email + "'" +
            ", smsNumber='" + smsNumber + "'" +
            '}';
    }
}
