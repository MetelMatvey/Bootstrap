package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> allUsers() {
        return  entityManager.createQuery("SELECT u FROM User u join fetch u.roleList").getResultList();
    }

    @Override
    public User show(long id) {
        return entityManager.find(User.class,id);
    }
    @Override
    public Optional<User> getUserByName(String name) {
        Query query= entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roleList WHERE u.name = :user");
        query.setParameter("user", name);
        Optional<User> user = query.getResultStream().findAny();
        user.orElse(null);
        return user;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
    @Override
    public void update(long id, User updateUser) {
        User user = entityManager.find(User.class,id);
        user.setName(updateUser.getName());
        user.setAge(updateUser.getAge());
        user.setEmail(updateUser.getEmail());

    }

    @Override
    public void delete(long id) {
        entityManager.createQuery("DELETE FROM User WHERE id=?1").setParameter(1,id).executeUpdate();

    }
}
