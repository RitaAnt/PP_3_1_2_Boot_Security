package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.AdminService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final AdminService adminService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, AdminService adminService) {
        this.successUserHandler = successUserHandler;
        this.adminService = adminService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .csrf().disable();

        // Добавляем инициализацию данных при старте приложения
        initRolesAndUsers();
    }

    private void initRolesAndUsers() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        // Сохраняем роли в базу данных
        if (adminService.getRoleByName("ROLE_ADMIN") == null) {
            adminService.saveRole(roleAdmin);
        }
        if (adminService.getRoleByName("ROLE_ADMIN") == null) {
            adminService.saveRole(roleUser);
        }

        // Создаем пользователя
        User user = new User();
        user.setUsername("admin");
        user.setPassword(adminService.getBCryptPasswordEncoder().encode("admin"));
        user.setEmail("admin@example.com");
        user.setRoles(Arrays.asList(roleAdmin, roleUser));

        // Сохраняем пользователя в базу данных
        adminService.saveUser(user);


        // Создаем пользователя
        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword(adminService.getBCryptPasswordEncoder().encode("user"));
        user2.setEmail("user@example.com");
        user2.setRoles(Arrays.asList(roleUser));

        // Сохраняем пользователя в базу данных
        adminService.saveUser(user2);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(adminService);
        return authenticationProvider;
    }
}
