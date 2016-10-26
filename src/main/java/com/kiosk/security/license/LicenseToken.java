package com.kiosk.security.license;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Token used for authenticated request from Kiosks by its licenses.
 */
public class LicenseToken extends AbstractAuthenticationToken {

    private final String licenseKey;

    public LicenseToken(String licenseKey) {
        super(null);
        this.licenseKey = licenseKey;
        setAuthenticated(false);
    }

    public LicenseToken(Collection<? extends GrantedAuthority> authorities, String licenseKey) {
        super(authorities);
        this.licenseKey = licenseKey;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.licenseKey;
    }

    @Override
    public Object getPrincipal() {
        return this.licenseKey;
    }

    public String getLicenseKey() {
        return licenseKey;
    }
}
