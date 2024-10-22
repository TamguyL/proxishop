package org.example.proxishop.Security;

import org.example.proxishop.model.entities.proxi.Shopkeepers;
import org.example.proxishop.service.ProxiShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ProxiShopService proxiShopService;

    @Bean
    public UserDetailsService userDetailsService() {

        return username -> {
            Shopkeepers shopkeepers = proxiShopService.findByEmail(username);
            if (shopkeepers != null) {
                return User.withUsername(shopkeepers.getEmail())
                        .password(shopkeepers.getPassword())
                        .roles("USER")
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/shopkeeper/login").permitAll()
                                .requestMatchers("/shopkeeper/dashboard").authenticated()
                                .requestMatchers("/shopkeeper/categories").authenticated()
                                .requestMatchers("/shopkeeper/orders").authenticated()
                                .requestMatchers("/shopkeeper/accountUpdate").authenticated()
                                .requestMatchers("/shopkeeper/accountCreation").permitAll() //autorise la création d'un nouveau compte sur proxishop
                                .requestMatchers("/shopkeeper/newbdd").permitAll()
                                .requestMatchers("/products/updateProducts").authenticated()
                                .anyRequest().permitAll()
                )

                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/shopkeeper/login")
                                .defaultSuccessUrl("/shopkeeper/dashboard")
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/shopkeeper/login")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}