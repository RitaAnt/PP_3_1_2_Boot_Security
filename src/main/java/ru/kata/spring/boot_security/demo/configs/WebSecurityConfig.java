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
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler,  UserService userService, RoleService roleService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.roleService = roleService;
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
    @Bean
    public RoleService roleService() {
        return roleService;
    }



    private void initRolesAndUsers() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        // Сохраняем роли в базу данных
        if (roleService.getRoleByName("ROLE_ADMIN") == null) {
            roleService.saveRole(roleAdmin);
        }
        if (roleService.getRoleByName("ROLE_USER") == null) {
            roleService.saveRole(roleUser);
        }

        // Создаем пользователя
        User user = new User();
        user.setUsername("admin");
        user.setPassword(userService.getBCryptPasswordEncoder().encode("admin"));
        user.setEmail("admin@example.com");
        user.setRoles(Arrays.asList(roleAdmin, roleUser));

        // Сохраняем пользователя в базу данных
        userService.saveUser(user);

        // Создаем пользователя
        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword(userService.getBCryptPasswordEncoder().encode("user"));
        user2.setEmail("user@example.com");
        user2.setRoles(Arrays.asList(roleUser));

        // Сохраняем пользователя в базу данных
        userService.saveUser(user2);
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
