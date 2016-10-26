package com.kiosk.security.license;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by kabachko on 10/4/2016.
 */
public class LicenseAuthenticationFilter extends OncePerRequestFilter {

    public static final String LICENSE_HEADER = "X-License";

    @Override
    protected void doFilterInternal(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        String license = request.getHeader(LICENSE_HEADER);
        if (license != null){
            Authentication auth = new LicenseToken(license.trim());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
