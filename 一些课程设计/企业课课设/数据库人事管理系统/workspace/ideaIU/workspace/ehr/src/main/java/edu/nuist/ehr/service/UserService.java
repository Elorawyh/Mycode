package edu.nuist.ehr.service;

import edu.nuist.ehr.bean.Role;
import edu.nuist.ehr.bean.User;
import edu.nuist.ehr.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 通过使用dao的findAll返回查询的数据
     * @return
     */
    public List<User> findAll(){
        return userDao.findAll();
    }

    /**
     * 插入user数据,同时还要插入user_role关联表的信息
     * @param user
     */
    @Transactional //开启mysql数据库的本地事务
    public void insert(User user){
        //1.调用UserDao的存储多个角色的方法
        //在完成插入user后，要获取到userid并且赋值给user对象
        userDao.insert(user);
        userDao.insertManyUserRole(user);
    }

    /**
     * 删除一条数据
     * @param id
     */
    public void delete(Long id){
        userDao.delete(id);
    }

    /**
     * 更新user表
     * @param user
     */
    public void update(User user){
        //更新角色相关信息步骤，1.先将和用户关联的老的数据删光，2.然后再插入新的
        userDao.deleteUserRole(user.getId());
        userDao.insertManyUserRole(user);
        userDao.update(user);
    }

    public User findById(Long id){
        return userDao.findById(id);
    }

    /**
     * 根据条件查询user信息
     * @param user
     * @return
     */
    public User find(User user) {
        return userDao.find(user);
    }

    /**
     * 根据条件查询多条记录
     * @param cond
     * @return
     */
    public List<User> findList(User cond) {
        return userDao.findList(cond);
    }
}
