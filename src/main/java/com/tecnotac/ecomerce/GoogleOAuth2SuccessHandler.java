package com.tecnotac.ecomerce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.tecnotac.ecomerce.model.Provider;
import com.tecnotac.ecomerce.model.Role;
import com.tecnotac.ecomerce.model.User;
import com.tecnotac.ecomerce.repository.RoleRepository;
import com.tecnotac.ecomerce.repository.UserRepository;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler{
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {		
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		String email = token.getPrincipal().getAttributes().get("email").toString();
		
		if (userRepository.findUserByEmail(email).isPresent()) {
			System.out.println("YA EXISTE");
		} else {
			User user = new User();
			user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
			user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
			user.setPicture(token.getPrincipal().getAttributes().get("picture").toString());
			user.setEnabled(true);
			user.setProvider(Provider.GOOGLE);
			user.setEmail(email);
			List<Role> roles = new ArrayList<>();
			roles.add(roleRepository.findById(2).get());
			user.setRoles(roles);
			userRepository.save(user);
			System.out.println("NUEVO USUARIO CREADO");
		}
		redirectStrategy.sendRedirect(request, response, "/myaccount");
	}
	
}
