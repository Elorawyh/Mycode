package edu.nuist.ehr.dao;

import edu.nuist.ehr.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用于对user表进行增删改查
 */
public interface UserDao {

    //查询user表的所有未删除数据
    @Select("select " +
            "  id," +
            "username," +
            "password," +
            "name," +
            "phone," +
            "del_flag as \"delFlag\"," +
            "create_time as \"createTime\"" +
            "from roles where del_flag!=1")
    List<User> findAll();

    @Insert("insert into users (" +
            "username," +
            "password," +
            "name," +
            "phone," +
            "create_time" +
            ") values(" +
            "#{username}," +
            "#{password}," +
            "#{name}," +
            "#{phone}," +
            "#{createTime}" +
            ")")
    @SelectKey(keyProperty = "id",before = false,keyColumn = "id", statement = "select last_insert_id()", resultType = Long.class)
    void insert(User user);

    @Delete("delete from users where id = #{id}")
    void delete(Long id);

    /**
     * 更新users表
     * @param user
     */
   /* @Update("update user set\n" +
            "         username = #{username},\n" +
            "         password = #{password},\n" +
            "         name = #{name},\n" +
            "         phone=#{phone}\n" +
            "        where id = #{id}")*/
    void update(User user);


    /**
     * 根据id查询user信息以及user对应的角色信息和菜单权限
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 根据条件查询user信息
     * @param user
     * @return
     */
    User find(User user);

    List<User> findList(User cond);


    /**
     * 向user_role表中存储关联数据
     */
    void insertManyUserRole(User user);

    /**
     * 删除某个用户的角色关联信息
     */
    @Delete("delete from user_role where user_id = #{userId}")
    void deleteUserRole(Long userId);

}