package com.tecnotac.ecomerce.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tecnotac.ecomerce.global.GlobalData;
import com.tecnotac.ecomerce.model.Role;
import com.tecnotac.ecomerce.model.User;
import com.tecnotac.ecomerce.repository.RoleRepository;
import com.tecnotac.ecomerce.repository.UserRepository;

@Controller
public class LoginController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "/views/account/login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "/views/account/register";
	}
	
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user,
			HttpServletRequest request) throws Exception{
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setEnabled(true);
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/";
	}
	
}
