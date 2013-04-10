package com.mypal.service;

import com.mypal.dao.UserDAO;
import com.mypal.entity.User;
import com.mypal.entity.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("myPalUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserDAO userDAO;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return buildUserFromUserEntity(userDAO.getByEmail(username));
    }

    public UserSecurity buildUserFromUserEntity(User user) {

        int id = user.getId();
        String username = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        double balance = user.getBalance();
        boolean enabled = user.isEnabled();

        UserSecurity userSecurity = new UserSecurity(id, username, password,
                user.getAuthorities(), firstName, balance, enabled);
        return userSecurity;
    }

}
