package com.kiosk.service.impl;

import com.kiosk.config.JHipsterProperties;
import com.kiosk.domain.Campaign;
import com.kiosk.domain.Card;
import com.kiosk.domain.User;
import com.kiosk.domain.enumeration.CampaignStatus;
import com.kiosk.domain.enumeration.CampaignType;
import com.kiosk.repository.CampaignRepository;
import com.kiosk.repository.CardRepository;
import com.kiosk.repository.UserRepository;
import com.kiosk.service.SmsService;
import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kabachko on 7/31/2016.
 */
@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger LOG = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private CampaignRepository campaignRepository;

    @Inject
    private CardRepository cardRepository;

    @Inject
    private UserRepository userRepository;

    @Override
    public void sendSms(List<String> numbers, String text) {
        final RestAPI plivoApi = new RestAPI(jHipsterProperties.getSms().getId(), jHipsterProperties.getSms().getToken(), jHipsterProperties.getSms().getVersion());
        LOG.info("Send sms message with text {} to next numbers:{}",text, numbers);
        if (StringUtils.isEmpty(text)|| numbers.isEmpty()){
            LOG.warn("Skip. Numbers or mesage is empty. Numbers: {}, Message: {}", numbers, text);
            return;
        }
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("src", jHipsterProperties.getSms().getFrom());
        parameters.put("dst", calculateDestinations(numbers)); // Receiver's phone number with country code
        parameters.put("text", text);
        try {
            MessageResponse response = plivoApi.sendMessage(parameters);
            LOG.info("Sms message sent with nex result: {}", response);
        } catch (PlivoException e) {
            LOG.error("Failed to send sms message", e);
        }
    }
    // List of numbers into  number<number<number format
    private String calculateDestinations(List<String> numbers) {
        return numbers.stream().collect(Collectors.joining("<"));
    }


    @Scheduled(cron = "0 */1 * * * *")
    public void sendPromotionCampaigns(){
        LOG.info("Promotion campaign scheduled");
        List<Campaign> newCampaigns = campaignRepository.findByCampaignTypeAndStatus(CampaignType.PROMOTION, CampaignStatus.NEW);
        for (Campaign campaign : newCampaigns){
            User user = campaign.getUser();
            List<Card> cards = cardRepository.findByUser(user.getId());
            if (user.getUserSettings().getSmsBalance() < cards.size()){
                LOG.info("Skip campaign {} due to not enough sms balance of user", campaign.getId());
                continue;
            }
            List<String> numbers = cards.stream().map(Card::getSmsNumber).collect(Collectors.toList());
            String text = campaign.getPromotion().getDescription();
            sendSms(numbers, text);
            campaign.setStatus(CampaignStatus.DELIVERED);
            campaignRepository.save(campaign);
        }
    }


    @Scheduled(cron = "0 */1 * * * *")
    public void sendCustomCampaigns(){
        LOG.info("Custom campaign scheduled");
        List<Campaign> newCampaigns = campaignRepository.findByCampaignTypeAndStatus(CampaignType.CUSTOM, CampaignStatus.NEW);
        for (Campaign campaign : newCampaigns){
            User user = campaign.getUser();
            List<Card> cards = cardRepository.findByUserAndType(user.getId(), campaign.getCardType());
            if (user.getUserSettings().getSmsBalance() < cards.size()){
                LOG.info("Skip campaign {} due to not enough sms balance of user", campaign.getId());
                continue;
            }
            List<String> numbers = cards.stream().map(Card::getSmsNumber).collect(Collectors.toList());
            String text = campaign.getCustomText();
            sendSms(numbers, text);
            minusSmsBalance(user, cards.size());
            campaign.setStatus(CampaignStatus.DELIVERED);
            campaignRepository.save(campaign);
        }
    }


    private void minusSmsBalance(User user, int size) {
        Integer currentBalance = user.getUserSettings().getSmsBalance();
        user.getUserSettings().setSmsBalance(currentBalance - size);
        userRepository.save(user);
    }


}
