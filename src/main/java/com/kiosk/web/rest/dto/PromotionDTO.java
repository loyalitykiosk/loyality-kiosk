package com.kiosk.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Promotion entity.
 */
public class PromotionDTO implements Serializable {

    private Long id;

    private String name;


    private String description;


    private LocalDate dateStart;


    private LocalDate dateEnd;


    @NotNull
    private String prizeName;


    private String prizeDescription;


    private Long userId;

    private String userCustomerName;

    private Long winnerId;

    private String winnerOwnerName;

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
    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }
    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }
    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }
    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
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

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long cardId) {
        this.winnerId = cardId;
    }

    public String getWinnerOwnerName() {
        return winnerOwnerName;
    }

    public void setWinnerOwnerName(String cardOwnerName) {
        this.winnerOwnerName = cardOwnerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PromotionDTO promotionDTO = (PromotionDTO) o;

        if ( ! Objects.equals(id, promotionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PromotionDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", dateStart='" + dateStart + "'" +
            ", dateEnd='" + dateEnd + "'" +
            ", prizeName='" + prizeName + "'" +
            ", prizeDescription='" + prizeDescription + "'" +
            '}';
    }
}
