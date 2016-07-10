package com.kiosk.web.rest.dto;

/**
 * Created by kabachko on 6/18/2016.
 */
public class CheckInDTO {

    private String kioskLicense;
    private String cardNumber;

    public CheckInDTO(String kioskLicense, String cardNumber) {
        this.kioskLicense = kioskLicense;
        this.cardNumber = cardNumber;
    }

    public String getKioskLicense() {
        return kioskLicense;
    }

    public void setKioskLicense(String number) {
        this.kioskLicense = number;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
