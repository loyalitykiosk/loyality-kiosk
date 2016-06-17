package com.kiosk.web.rest.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the CardTransaction entity.
 */
public class CardTransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime timestamp;


    @NotNull
    private Long cardId;


    @NotNull
    private Double balanceBefore;


    @NotNull
    private Double balanceAfter;


    @NotNull
    private Long kioskId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
    public Double getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(Double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }
    public Double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    public Long getKioskId() {
        return kioskId;
    }

    public void setKioskId(Long kioskId) {
        this.kioskId = kioskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CardTransactionDTO cardTransactionDTO = (CardTransactionDTO) o;

        if ( ! Objects.equals(id, cardTransactionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CardTransactionDTO{" +
            "id=" + id +
            ", timestamp='" + timestamp + "'" +
            ", cardId='" + cardId + "'" +
            ", balanceBefore='" + balanceBefore + "'" +
            ", balanceAfter='" + balanceAfter + "'" +
            ", kioskId='" + kioskId + "'" +
            '}';
    }
}
