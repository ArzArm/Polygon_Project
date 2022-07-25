package com.example.spring.service;

import com.example.spring.entity.User;
import com.example.spring.exception.UsernameDuplicatedException;
import com.example.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("The invalid username"));
        return new SecuredUser(user);
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this).passwordEncoder(passwordEncoder());
    }


    public void saveUser(User user) {
        if(userRepository.findUserByUsername(user.getUsername()).isPresent()){
            throw new UsernameDuplicatedException("'" + user.getUsername() + "' username is already used please choose another username");
        }
        user.setRole("user");
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public static class SecuredUser implements UserDetails {
         private final transient User user;

        public SecuredUser(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthorityImpl> authorityList = new ArrayList<>();
            authorityList.add(new GrantedAuthorityImpl(user.getRole()));
            return authorityList;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

    public static class GrantedAuthorityImpl implements GrantedAuthority {
        private final String role;

        GrantedAuthorityImpl(String role) {
            this.role = role;
        }

        @Override
        public String getAuthority() {
            return "ROLE_" + role;
        }
    }

}




