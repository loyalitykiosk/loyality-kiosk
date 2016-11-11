package com.kiosk.domain;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.kiosk.domain.enumeration.CardType;

import com.kiosk.domain.enumeration.CampaignType;

import com.kiosk.domain.enumeration.CampaignStatus;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

/**
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 140)
    @Column(name = "custom_text", length = 140)
    private String customText;

    @ElementCollection(targetClass = CardType.class,fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "campaign_card_types", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "card_type", nullable = false)
    private Collection<CardType> cardType;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CampaignType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CampaignStatus status;

    @Column(name = "status_date")
    private LocalDate statusDate;

    @Column(name = "status_description")
    private String statusDescription;

    @ManyToOne
    private User user;

    @ManyToOne
    private Promotion promotion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomText() {
        return customText;
    }

    public void setCustomText(String customText) {
        this.customText = customText;
    }

    public Collection<CardType> getCardType() {
        return cardType;
    }

    public void setCardType(Collection<CardType> cardType) {
        this.cardType = cardType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CampaignType getType() {
        return type;
    }

    public void setType(CampaignType type) {
        this.type = type;
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public LocalDate getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDate statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Campaign campaign = (Campaign) o;
        if(campaign.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, campaign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + id +
            ", customText='" + customText + "'" +
            ", date='" + date + "'" +
            ", type='" + type + "'" +
            ", status='" + status + "'" +
            ", statusDate='" + statusDate + "'" +
            ", statusDescription='" + statusDescription + "'" +
            '}';
    }
}
