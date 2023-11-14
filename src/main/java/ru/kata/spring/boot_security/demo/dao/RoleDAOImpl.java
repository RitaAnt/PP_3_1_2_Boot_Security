package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.Role;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public RoleDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Role> getAllRoles() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Role> query = session.createQuery("from Role", Role.class);
        return query.getResultList();
    }

    @Override
    public Role getRoleById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Role.class, id);
    }

    @Override
    public Role getRoleByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Role> query = session.createQuery("from Role where name = :name", Role.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void saveRole(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(role);
    }

    @Override
    public void deleteRole(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Role role = session.get(Role.class, id);
        session.delete(role);
    }
}
