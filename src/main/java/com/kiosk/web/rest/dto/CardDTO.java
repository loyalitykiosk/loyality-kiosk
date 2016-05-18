package com.kiosk.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.kiosk.domain.enumeration.CardStatus;
import com.kiosk.domain.enumeration.CardType;

/**
 * A DTO for the Card entity.
 */
public class CardDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    private String number;


    @NotNull
    private String ownerName;


    @NotNull
    private String ownerSurname;


    @NotNull
    private LocalDate ownerBirthDate;


    @NotNull
    private CardStatus status;


    @NotNull
    private CardType type;


    private Long userId;

    private String userCustomerName;

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
    public LocalDate getOwnerBirthDate() {
        return ownerBirthDate;
    }

    public void setOwnerBirthDate(LocalDate ownerBirthDate) {
        this.ownerBirthDate = ownerBirthDate;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCustomerName() {
        return userCustomerName;
    }

    public void setUserCustomerName(String userCustomerName) {
        this.userCustomerName = userCustomerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CardDTO cardDTO = (CardDTO) o;

        if ( ! Objects.equals(id, cardDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CardDTO{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", ownerName='" + ownerName + "'" +
            ", ownerSurname='" + ownerSurname + "'" +
            ", ownerBirthDate='" + ownerBirthDate + "'" +
            ", status='" + status + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
