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

}
