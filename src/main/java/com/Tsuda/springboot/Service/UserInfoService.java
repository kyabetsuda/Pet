package com.Tsuda.springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Tsuda.springboot.Repository.UserRepository;
import com.Tsuda.springboot.model.User;

@Service
public class UserInfoService implements UserDetailsService{

	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    if (username == null || "".equals(username)) {
	      throw new UsernameNotFoundException("Username is empty");
	    }

	    User user = repository.findByUsername(username);
	    if (user == null) {
	      throw new UsernameNotFoundException("User not found for name: " + username);
	    }

	    return user;
	  }




}
