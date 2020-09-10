package com.webapp.rentalapp.controller;



import com.webapp.rentalapp.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);


    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Client client = clientRepository.findByUsername(username);
        if(client==null) {
            throw new UsernameNotFoundException("No Username in DataBase"); }
        return new org.springframework.security.core.userdetails.User(client.getUsername(),client.getPassword(),getAuthorities(client));
    }

    private static Collection<?extends GrantedAuthority> getAuthorities(Client client){
        String[] userRoles= client.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority>authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }

}
