package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleDao {
    public Role showRole(String name);
    public List<Role> getRoleList();
    public void save(Role role);
    public Role getIdRole(long id);
}
