package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao {
        @PersistenceContext
        EntityManager entityManager;
        @Override
        public Role showRole (String name) {
            return (Role) entityManager.createQuery("FROM Role r WHERE role =?1 ").setParameter(1,name).getSingleResult();
        }

        @Override
        public List<Role> getRoleList() {
                return entityManager.createQuery("SELECT r from Role r").getResultList();
        }
        @Override
        @Transactional
        public void save(Role role) {
                entityManager.persist(role);
        }

        @Override
        public Role getIdRole(long id) {
                return entityManager.find(Role.class,id);
        }
}
