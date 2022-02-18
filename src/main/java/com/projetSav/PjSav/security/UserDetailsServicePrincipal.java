package com.projetSav.PjSav.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projetSav.PjSav.dao.ClientRepository;
import com.projetSav.PjSav.model.Client;
import com.projetSav.PjSav.model.Role;

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
		return new User(clt.getEmail(), clt.getPassword(),  mapRolesToAuthorities(clt.getRoles()));
	}
	
    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getLibelle())).collect(Collectors.toList());
    }

}
