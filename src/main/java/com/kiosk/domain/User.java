package com.kiosk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.time.ZonedDateTime;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
public class User extends AbstractAuditingEntity implements Serializable {

    public User() {
        this.setSilverBarier(0l);
        this.setGoldBarier(0l);
        this.setPlatinumBarier(0l);
        this.setCheckinTimeout(0l);
        this.setPlatinumPoints(0l);
        this.setGoldPoints(0l);
        this.setSilverPoints(0l);
        this.setBronzePoints(0l);
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-z0-9]*$|(anonymousUser)")
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash",length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date", nullable = true)
    private ZonedDateTime resetDate = null;

    @NotNull
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_details")
    private String customerDetails;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<Authority> authorities = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<PersistentToken> persistentTokens = new HashSet<>();

    @NotNull
    @Column(name = "platinum_points", nullable = false)
    private Long platinumPoints;

    @NotNull
    @Column(name = "gold_points", nullable = false)
    private Long goldPoints;

    @NotNull
    @Column(name = "silver_points", nullable = false)
    private Long silverPoints;

    @NotNull
    @Column(name = "bronze_points", nullable = false)
    private Long bronzePoints;

    @NotNull
    @Column(name = "silver_barier", nullable = false)
    private Long silverBarier;

    @NotNull
    @Column(name = "gold_barier", nullable = false)
    private Long goldBarier;

    @NotNull
    @Column(name = "platinum_barier", nullable = false)
    private Long platinumBarier;

    @NotNull
    @Column(name = "checkin_timeout", nullable = false)
    private Long checkinTimeout;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne
    private Subscription subscription;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private UserSettings userSettings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTime getResetDate() {
       return resetDate;
    }

    public void setResetDate(ZonedDateTime resetDate) {
       this.resetDate = resetDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(String customerDetails) {
        this.customerDetails = customerDetails;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<PersistentToken> getPersistentTokens() {
        return persistentTokens;
    }

    public void setPersistentTokens(Set<PersistentToken> persistentTokens) {
        this.persistentTokens = persistentTokens;
    }

    public Long getPlatinumPoints() {
        return platinumPoints;
    }

    public void setPlatinumPoints(Long platinumPoints) {
        this.platinumPoints = platinumPoints;
    }

    public Long getGoldPoints() {
        return goldPoints;
    }

    public void setGoldPoints(Long goldPoints) {
        this.goldPoints = goldPoints;
    }

    public Long getSilverPoints() {
        return silverPoints;
    }

    public void setSilverPoints(Long silverPoints) {
        this.silverPoints = silverPoints;
    }

    public Long getBronzePoints() {
        return bronzePoints;
    }

    public void setBronzePoints(Long bronzePoints) {
        this.bronzePoints = bronzePoints;
    }

    public Long getSilverBarier() {
        return silverBarier;
    }

    public void setSilverBarier(Long silverBarier) {
        this.silverBarier = silverBarier;
    }

    public Long getGoldBarier() {
        return goldBarier;
    }

    public void setGoldBarier(Long goldBarier) {
        this.goldBarier = goldBarier;
    }

    public Long getPlatinumBarier() {
        return platinumBarier;
    }

    public void setPlatinumBarier(Long platinumBarier) {
        this.platinumBarier = platinumBarier;
    }

    public Long getCheckinTimeout() {
        return checkinTimeout;
    }

    public void setCheckinTimeout(Long checkinTimeout) {
        this.checkinTimeout = checkinTimeout;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!login.equals(user.login)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            ", customerName='" + customerName + '\'' +
            '}';
    }
}
