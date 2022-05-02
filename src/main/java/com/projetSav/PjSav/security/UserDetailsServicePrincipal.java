package com.projetSav.PjSav.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.projetSav.PjSav.dao.ClientRepository;
import com.projetSav.PjSav.model.Client;


@Service
public class UserDetailsServicePrincipal implements UserDetailsService {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client clt = new Client();
		try {
		clt = clientRepository.findByEmail(username);
		}
		catch(Exception e) {e.getMessage();}
		return new User(clt.getUsername(), clt.getPassword(),  clt.getAuthorities());
	}
}
