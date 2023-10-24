package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminServiceImpl implements AdminService {
    private final EntityManager entityManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminServiceImpl(EntityManager entityManager, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this. entityManager = entityManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }
    @Override
    public User getUserByName(String name) {
        return entityManager.createQuery("SELECT username FROM User username WHERE username.username = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }
    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        if (user != null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            entityManager.persist(user);
        }
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        entityManager.remove(entityManager.find(User.class,id));
    }

    @Transactional
    @Override
    public void updateUser(long id, User user) {
        User editUser = entityManager.find(User.class, id);
        if (editUser != null) {
            editUser.setUsername(user.getUsername());
            editUser.setEmail(user.getEmail());
            editUser.setRoles(user.getRoles());
            if (!editUser.getPassword().equals(user.getPassword())) {
                editUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
        }
    }

    @Override
    public List<Role> getListRoles(long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            return user.getRoles();
        }
        return null;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Query query = entityManager.createQuery
                ("select u from User u left join fetch u.roles where u.username=:name", User.class);
        query.setParameter("name", name);
        User user = (User) query.getSingleResult();
        if (user == null){
            throw new UsernameNotFoundException(String.format("User %s not found",name));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}