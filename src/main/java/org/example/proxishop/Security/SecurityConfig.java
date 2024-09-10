package org.example.proxishop.Security;

import org.example.proxishop.model.entities.shopkeeper.Shopkeeper;
import org.example.proxishop.service.ShopkeeperService;
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
    private ShopkeeperService shopkeeperService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Shopkeeper shopkeeper = shopkeeperService.findByEmail(username);
            if (shopkeeper != null) {
                return User.withUsername(shopkeeper.getEmail())
                        .password(shopkeeper.getPassword())
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
                                .requestMatchers("/shopkeeper/newbdd").permitAll() // Autoriser la création de base de données
                                .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/shopkeeper/newbdd")) // Désactiver CSRF pour /shopkeeper/newbdd
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