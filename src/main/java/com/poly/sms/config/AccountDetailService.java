package com.poly.sms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.poly.sms.entity.Employee;
import com.poly.sms.service.EmployeeService;

import jakarta.transaction.Transactional;

@Service
@Component
public class AccountDetailService implements UserDetailsService {

    @Autowired
    EmployeeService employeeService;

    @Override
    @Transactional // note :(
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Employee employee = employeeService.findByUsername(username);
            if (employee == null) {
                throw new UsernameNotFoundException(username + " NOT FOUND");
            }

            String password = employee.getPassword();
            String[] roles = employee.getEmployeeRoles()
                    .stream()
                    .map(au -> au.getRole().getRoleId())
                    .toArray(String[]::new);

            return User.builder()
                    .username(username)
                    .password(password)
                    .roles(roles)
                    .build();

        } catch (Exception e) {
            throw new UsernameNotFoundException(username + " NOT FOUND", e);
        }

    }

    public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
        String email = oauth2.getPrincipal().getAttribute("email");
        String password = Long.toHexString(System.currentTimeMillis());

        UserDetails user = User.withUsername(email).password(new BCryptPasswordEncoder().encode(password))
                .roles("GUEST").build();

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
