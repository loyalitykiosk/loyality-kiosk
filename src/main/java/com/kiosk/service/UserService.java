package com.kiosk.service;

import com.kiosk.domain.*;
import com.kiosk.repository.*;
import com.kiosk.security.SecurityUtils;
import com.kiosk.service.util.RandomUtil;
import com.kiosk.web.rest.dto.ManagedUserDTO;
import java.time.ZonedDateTime;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import java.util.*;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);


    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    @Inject
    private SmsService smsService;

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private SubscriptionRepository subscriptionRepository;

    @Inject
    private CardRepository cardRepository;

    @Inject
    private CampaignRepository campaignRepository;

    @Inject
    private KioskRepository kioskRepository;

    @Inject
    private PromotionRepository promotionRepository;



    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                userRepository.save(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
       log.debug("Reset user password for reset key {}", key);

       return userRepository.findOneByResetKey(key)
            .filter(user -> {
                ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
                return user.getResetDate().isAfter(oneDayAgo);
           })
           .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                userRepository.save(user);
                return user;
           });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(ZonedDateTime.now());
                userRepository.save(user);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail, String phone) {
        return userRepository.findOneByEmailAndPhone(mail, phone)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(ZonedDateTime.now());
                userRepository.save(user);
                return user;
            });
    }

    public User createUserInformation(String login, String password, String firstName, String lastName, String email,
        String langKey) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne("ROLE_USER");
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
//        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(ManagedUserDTO managedUserDTO) {
        User user = new User();
        user.setLogin(managedUserDTO.getLogin());
        user.setFirstName(managedUserDTO.getFirstName());
        user.setLastName(managedUserDTO.getLastName());
        user.setEmail(managedUserDTO.getEmail());
        user.setPhone(managedUserDTO.getPhone());
        user.setCustomerName(managedUserDTO.getCustomerName());
        user.setCustomerDetails(managedUserDTO.getCustomerDetails());
        Subscription subscription = subscriptionRepository.findOne(managedUserDTO.getSubscriptionId());
        user.setSubscription(subscription);
        user.setUserSettings(new UserSettings());
        user.getUserSettings().setSmsBalance(subscription.getSmsPlan());

        if (managedUserDTO.getLangKey() == null) {
            user.setLangKey("en"); // default language
        } else {
            user.setLangKey(managedUserDTO.getLangKey());
        }
        if (managedUserDTO.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            managedUserDTO.getAuthorities().stream().forEach(
                authority -> authorities.add(authorityRepository.findOne(authority))
            );
            user.setAuthorities(authorities);
        }
        String planTextPassword = RandomUtil.generatePassword();
        String encryptedPassword = passwordEncoder.encode(planTextPassword);
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(ZonedDateTime.now());
        user.setActivated(true);
        userRepository.save(user);
        smsService.sendCreationMessage(user, planTextPassword);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    public void updateUserInformation(String firstName, String lastName, String email, String langKey,
                                      Long platinumPoints,Long goldPoints, Long silverPoints, Long bronzePoints,
                                      Long silverBarier,Long goldBarier,Long platinumBarier,Long checkinTimeout) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setEmail(email);
            u.setLangKey(langKey);
            u.setPlatinumPoints(platinumPoints);
            u.setGoldPoints(goldPoints);
            u.setSilverPoints(silverPoints);
            u.setBronzePoints(bronzePoints);
            u.setSilverBarier(silverBarier);
            u.setGoldBarier(goldBarier);
            u.setPlatinumBarier(platinumBarier);
            u.setCheckinTimeout(checkinTimeout);
            userRepository.save(u);
            log.debug("Changed Information for User: {}", u);
        });
    }

    //TODO remove user's kiosks, cards, campaigns

    public void deleteUserInformation(String login) {
        userRepository.findOneByLogin(login).ifPresent(u -> {
            deleteUsersCards(u);
            deleteUsersCampaigns(u);
            deleteUsersKiosks(u);
            deleteUsersPromotions(u);
            userRepository.delete(u);
            log.debug("Deleted User: {}", u);
        });
    }

    private void deleteUsersPromotions(User u) {
        promotionRepository.findByUser(u.getId()).forEach(promotion -> {
            promotionRepository.delete(promotion.getId());
        });
    }

    private void deleteUsersKiosks(User u) {
        kioskRepository.findByUser(u.getId()).forEach(kiosk -> {
            kioskRepository.delete(kiosk.getId());
        });
    }

    private void deleteUsersCampaigns(User u) {
        campaignRepository.findByUser(u.getId()).forEach(campaign -> {
            campaignRepository.delete(campaign.getId());
        });
    }

    private void deleteUsersCards(User user) {
        cardRepository.findByUser(user.getId()).forEach(card -> {
            cardRepository.delete(card.getId());
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
            String encryptedPassword = passwordEncoder.encode(password);
            u.setPassword(encryptedPassword);
            userRepository.save(u);
            log.debug("Changed password for User: {}", u);
        });
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByLogin(login).map(u -> {
            u.getAuthorities().size();
            return u;
        });
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        User user = userRepository.findOne(id);
        user.getAuthorities().size(); // eagerly load the association
        return user;
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        User user = getUserWithOutAuthorities();
        user.getAuthorities().size(); // eagerly load the association
        return user;
    }

    @Transactional(readOnly = true)
    public User getUserWithOutAuthorities() {
        return  userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
    }

    /**
     * Persistent Token are used for providing automatic authentication, they should be automatically deleted after
     * 30 days.
     * <p>
     * This is scheduled to get fired everyday, at midnight.
     * </p>
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOldPersistentTokens() {
        LocalDate now = LocalDate.now();
        persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).stream().forEach(token -> {
            log.debug("Deleting token {}", token.getSeries());
            User user = token.getUser();
            user.getPersistentTokens().remove(token);
            persistentTokenRepository.delete(token);
        });
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }

    /**
     * Reset sms balance for evety user once a month based on chosen subscription
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void renewUserSettings(){
        List<User> users = userRepository.findAll();
        for (User user : users) {
            log.debug("Renew user's settings {}", user.getLogin());
            Integer smsNumber = user.getSubscription().getSmsPlan();
            user.getUserSettings().setSmsBalance(smsNumber);
            userRepository.save(user);
        }
    }
}
