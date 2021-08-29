package com.tecnotac.ecomerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tecnotac.ecomerce.model.CustomUserDetail;
import com.tecnotac.ecomerce.model.User;
import com.tecnotac.ecomerce.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findUserByEmail(email);
		user.orElseThrow(() -> new UsernameNotFoundException("User Exception"));
		return user.map(CustomUserDetail::new).get();
	}
	
	@Autowired
	UserRepository productRepository;
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
	
	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}
	
}
