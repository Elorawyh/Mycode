package edu.nuist.ehr.dao;

import edu.nuist.ehr.bean.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {

    //查询所有roles表中没有删除的信息
    @Select("select id, role_name roleName from roles")
    List<Role> findAll();
    void update(Role role);

    @Insert("insert into roles(id,role_name) values(#{id},#{roleName})")
    @SelectKey(keyProperty = "id",before = false,keyColumn = "id", statement = "select last_insert_id()", resultType = Long.class)
    void insert(Role role);
    void insertManyRoleMenu(Role role);

    @Delete("delete from roles where id = #{id}")
    void delete(Long id);


    List<Role> findList(Role cond);

    @Delete("delete from roles_menus where role_id = #{roleId}")
    void deleteRoleMenu(Long roleId);

}