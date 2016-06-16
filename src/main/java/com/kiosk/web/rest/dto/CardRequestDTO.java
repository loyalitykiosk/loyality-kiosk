package com.kiosk.web.rest.dto;

import com.kiosk.domain.enumeration.CardStatus;
import com.kiosk.domain.enumeration.CardType;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the CardRequest entity.
 */
public class CardRequestDTO implements Serializable {

    private Long id;

    private String cardNumber;

    @NotNull
    private String ownerName;


    @NotNull
    private String ownerSurname;


    private String email;


    @NotNull
    private String smsNumber;


    private Long kioskId;

    private String kioskName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public Long getKioskId() {
        return kioskId;
    }

    public void setKioskId(Long kioskId) {
        this.kioskId = kioskId;
    }

    public String getKioskName() {
        return kioskName;
    }

    public void setKioskName(String kioskName) {
        this.kioskName = kioskName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CardRequestDTO cardRequestDTO = (CardRequestDTO) o;

        if ( ! Objects.equals(id, cardRequestDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CardRequestDTO{" +
            "id=" + id +
            ", ownerName='" + ownerName + "'" +
            ", ownerSurname='" + ownerSurname + "'" +
            ", email='" + email + "'" +
            ", smsNumber='" + smsNumber + "'" +
            '}';
    }


    public static CardDTO createCardDTO(CardRequestDTO cardRequestDTO){
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNumber(cardRequestDTO.getCardNumber());
        cardDTO.setEmail(cardRequestDTO.getEmail());
        cardDTO.setOwnerName(cardRequestDTO.getOwnerName());
        cardDTO.setOwnerSurname(cardRequestDTO.getOwnerSurname());
        cardDTO.setSmsNumber(cardRequestDTO.getSmsNumber());
        cardDTO.setType(CardType.BRONZE);
        cardDTO.setStatus(CardStatus.blocked);
        return cardDTO;
    }
}
