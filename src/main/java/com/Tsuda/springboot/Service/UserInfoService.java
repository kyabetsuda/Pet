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
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		int userid_int = 0;
		
		try {
			userid_int = Integer.parseInt(userid);
		}catch(Exception e){
			
		}
		
	    if ( userid == null || "".equals(userid)) {
		    	try {
		      throw new UsernameNotFoundException("Username is empty");
		    	}catch(Exception e) {
		    		System.out.println(e.getMessage());
		    	}
	    }

	    User user = repository.findByUserid(userid_int);
	    if (user == null) {
	      throw new UsernameNotFoundException("User not found for id: " + userid);
	    }

	    return user;
	  }




}
