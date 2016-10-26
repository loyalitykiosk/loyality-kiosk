package com.kiosk.security.license;

import com.kiosk.domain.Kiosk;
import com.kiosk.domain.User;
import com.kiosk.repository.KioskRepository;
import com.kiosk.security.UserNotActivatedException;
import com.kiosk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LicenseAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private KioskRepository kioskRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LicenseToken token = (LicenseToken)authentication;
        Kiosk kiosk = kioskRepository.findByLicense(token.getLicenseKey());
        if (kiosk == null){
            throw new BadCredentialsException("invalid license:" + token.getLicenseKey());
        }
        User kioskUser = kiosk.getCustomer();
        if (kioskUser == null){
            throw new UsernameNotFoundException("User not found by provided license");
        }
        if (!kioskUser.getActivated()){
            throw new UserNotActivatedException("User is not activated");
        }
        kioskUser = userService.getUserWithAuthorities(kioskUser.getId());
        UserDetails userDetails = userDetailsService.loadUserByUsername(kioskUser.getLogin());
        List<GrantedAuthority> authorities =  kioskUser.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        authentication = new UsernamePasswordAuthenticationToken(userDetails,kioskUser.getLogin(),authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LicenseToken.class.isAssignableFrom(authentication);
    }

    public KioskRepository getKioskRepository() {
        return kioskRepository;
    }

    public void setKioskRepository(KioskRepository kioskRepository) {
        this.kioskRepository = kioskRepository;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
