package com.kiosk.service;

import java.util.List;

/**
 * Service for sending sms messages.
 */
public interface SmsService {

    void sendSms(List<String> numbers, String text);

    void sendPromotionCampaigns();

    void sendCustomCampaigns();
}
