package com.shoptech.admin.security;


import com.shoptech.admin.user.UserRepository;
import com.shoptech.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ShoptechUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new ShoptechUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Không tìm thấy tài khoản với eamil: " + email);
	}

}
