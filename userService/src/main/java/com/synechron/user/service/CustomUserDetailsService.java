package com.synechron.user.service;

import com.synechron.user.model.AppUser;
import com.synechron.user.model.CustomUserDetails;
import com.synechron.user.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = appUserRepository.findByUserName(username);

        if (appUser == null) {
            throw new UsernameNotFoundException("User name not found ");
        }

        return new CustomUserDetails(appUser);
    }
}
