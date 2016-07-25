package com.kiosk.domain.enumeration;

import com.kiosk.domain.Card;
import com.kiosk.domain.User;

/**
 * The CardType enumeration.
 */
public enum CardType {
    BRONZE,SILVER,GOLD,PLATINUM;

    public Long getPoints(User user){
        switch (this){
            case BRONZE:  return  user.getBronzePoints();
            case SILVER:  return  user.getSilverPoints();
            case GOLD:  return  user.getGoldPoints();
            case PLATINUM:  return  user.getPlatinumPoints();
            default: return 0l;
        }
    }

    public CardType upgrade(User user, Card card){
        switch (this){
            case GOLD:  return card.getBalance() >= user.getPlatinumBarier() ? PLATINUM : this;
            case SILVER: return card.getBalance() >= user.getGoldBarier() ? GOLD : this;
            case BRONZE:  return card.getBalance() >= user.getSilverBarier() ? SILVER : this;
            default: return  this;
        }
    }

}
