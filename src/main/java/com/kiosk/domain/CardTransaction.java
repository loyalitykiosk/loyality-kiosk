package com.kiosk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CardTransaction.
 */
@Entity
@Table(name = "card_transaction")
public class CardTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "transaction_timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @NotNull
    @Column(name = "card_id", nullable = false)
    private Long cardId;

    @NotNull
    @Column(name = "balance_before", nullable = false)
    private Double balanceBefore;

    @NotNull
    @Column(name = "balance_after", nullable = false)
    private Double balanceAfter;

    @NotNull
    @Column(name = "kiosk_id", nullable = false)
    private Long kioskId;

    public CardTransaction() {
    }

    public CardTransaction(ZonedDateTime timestamp, Long cardId, Double balanceBefore, Double balanceAfter, Long kioskId) {
        this.timestamp = timestamp;
        this.cardId = cardId;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.kioskId = kioskId;
    }

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
        CardTransaction cardTransaction = (CardTransaction) o;
        if(cardTransaction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cardTransaction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CardTransaction{" +
            "id=" + id +
            ", timestamp='" + timestamp + "'" +
            ", cardId='" + cardId + "'" +
            ", balanceBefore='" + balanceBefore + "'" +
            ", balanceAfter='" + balanceAfter + "'" +
            ", kioskId='" + kioskId + "'" +
            '}';
    }
}
