package com.kiosk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserSettings.
 */
@Entity
@Table(name = "user_settings")
public class UserSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "sms_balance", nullable = false)
    private Integer smsBalance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSmsBalance() {
        return smsBalance;
    }

    public void setSmsBalance(Integer smsBalance) {
        this.smsBalance = smsBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserSettings userSettings = (UserSettings) o;
        if(userSettings.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userSettings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserSettings{" +
            "id=" + id +
            ", smsBalance='" + smsBalance + "'" +
            '}';
    }
}
