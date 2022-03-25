package edu.nuist.ehr.service;

import edu.nuist.ehr.bean.Role;
import edu.nuist.ehr.dao.RoleDao;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public List<Role> findAll(){
        return roleDao.findAll();
    }

    public List<Role> findList(Role cond) {
        return roleDao.findList(cond);
    }

    public void delete(Long id) {
        roleDao.delete(id);
    }

    public void update(Role role) {
        roleDao.deleteRoleMenu(role.getId());
        roleDao.insertManyRoleMenu(role);
        roleDao.update(role);
    }

    public void insert(Role role) {
        roleDao.insert(role);
        roleDao.insertManyRoleMenu(role);
    }
}
