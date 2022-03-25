package edu.nuist.ehr.dao;

import edu.nuist.ehr.bean.Menu;
import edu.nuist.ehr.bean.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

public interface MenuDao {

    @Select("select id, menu_name menuName,url from menus")
    List<Menu> findAll();

    List<Menu> findList(Menu menu);

    @Insert("insert into menus (" +
            "menu_name,url" +
            ") values(" +
            "#{menuName},#{url}" +
            ")")
    @SelectKey(keyProperty = "id", before = false, keyColumn = "id", statement = "select last_insert_id()",
            resultType = Long.class)
    void insert(Menu menu);

    @Delete("delete from roles_menus where menu_id = #{menuId}")
    void deleteRoleMenu(Long menuId);

    void update(Menu menu);

    @Delete("delete from menus where id = #{id}")
    void delete(Long id);


}
