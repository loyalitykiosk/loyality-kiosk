package com.kiosk.web.rest.dto;

import com.kiosk.domain.Authority;
import com.kiosk.domain.User;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.*;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    @NotNull
    private String customerName;


    private String customerDetails;

    private Set<String> authorities;

    private Long platinumPoints;

    private Long goldPoints;

    private Long silverPoints;

    private Long bronzePoints;

    @NotNull
    private Long silverBarier;


    @NotNull
    private Long goldBarier;


    @NotNull
    private Long platinumBarier;


    @NotNull
    private Long checkinTimeout;



    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getLogin(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getActivated(), user.getLangKey(),
            user.getCustomerName(), user.getCustomerDetails(), user.getPlatinumPoints(), user.getGoldPoints(), user.getSilverPoints(),
            user.getBronzePoints(), user.getSilverBarier(), user.getGoldBarier(), user.getPlatinumBarier(), user.getCheckinTimeout(),
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet()));
    }

    public UserDTO(String login, String firstName, String lastName, String email, boolean activated,
                    String langKey,String customerName, String customerDetails, Long platinumPoints,
                   Long goldPoints, Long silverPoints, Long bronzePoints,Long silverBarier,
                   Long goldBarier,Long platinumBarier,Long checkinTimeout,Set<String> authorities) {

        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.authorities = authorities;
        this.customerName = customerName;
        this.customerDetails = customerDetails;
        this.platinumPoints = platinumPoints;
        this.goldPoints = goldPoints;
        this.silverPoints = silverPoints;
        this.bronzePoints = bronzePoints;
        this.silverBarier = silverBarier;
        this.goldBarier = goldBarier;
        this.platinumBarier = platinumBarier;
        this.checkinTimeout = checkinTimeout;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerDetails() {
        return customerDetails;
    }

    public Set<String> getAuthorities() {
        return authorities;
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


    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", authorities=" + authorities +
            "}";
    }
}
