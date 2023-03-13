package ru.kata.spring.boot_security.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

@Service
@Transactional
public class RoleServiceImp implements RoleService {

    private final RoleDao roleDAO;

    @Autowired
    public RoleServiceImp(RoleDao roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> findAllRole() {
        return roleDAO.getRoleList();
    }

    }

