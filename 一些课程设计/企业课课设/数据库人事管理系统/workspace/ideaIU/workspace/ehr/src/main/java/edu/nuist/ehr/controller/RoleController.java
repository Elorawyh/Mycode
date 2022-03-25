package edu.nuist.ehr.controller;



import edu.nuist.ehr.bean.Role;
import edu.nuist.ehr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/role/all")
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @PostMapping("/role/find")
    public List<Role> findList(@RequestBody Role cond){
        return  roleService.findList(cond);
    }

    @PostMapping("/role")
    public String insert(@RequestBody Role role){
        roleService.insert(role);
        return "insert success";
    }

    @DeleteMapping("role/{uid}")
    public String delete(@PathVariable("uid") Long id){
        roleService.delete(id);
        return "delete success";
    }


    @PostMapping("/role/update")
    public String update(@RequestBody Role role) {
        roleService.update(role);
        return "update success";
    }


}