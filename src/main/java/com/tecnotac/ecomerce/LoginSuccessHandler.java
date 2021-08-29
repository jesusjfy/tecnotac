package com.tecnotac.ecomerce;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.tecnotac.ecomerce.model.CustomUserDetail;
import com.tecnotac.ecomerce.repository.RoleRepository;
import com.tecnotac.ecomerce.repository.UserRepository;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {		
		
		CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
		
        String redirectURL = request.getContextPath();
        
        if (userDetails.hasRole("ROLE_ADMIN")) {
            redirectURL = "/admin";
        } else {
            redirectURL = "/myaccount";
        } 
         
		redirectStrategy.sendRedirect(request, response, redirectURL);
	}
	
}
