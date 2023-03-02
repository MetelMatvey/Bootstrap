package ru.kata.spring.boot_security.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImp implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role showRole(String name) {
        return roleDao.showRole(name);
    }

    @Override
    public List<Role> getRoleList() {
        return roleDao.getRoleList();
    }
}
