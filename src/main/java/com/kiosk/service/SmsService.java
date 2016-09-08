package com.kiosk.service;

import com.kiosk.domain.Card;
import com.kiosk.domain.User;

import java.util.List;

/**
 * Service for sending sms messages.
 */
public interface SmsService {

    void sendSms(List<String> numbers, String text);

    void sendPromotionCampaigns();

    void sendCustomCampaigns();

    void sendCreationMessage(User user, String password);

    void sendCardCreation(Card card);
}
