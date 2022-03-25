package edu.nuist.ehr.controller;

import edu.nuist.ehr.bean.Menu;
import edu.nuist.ehr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu/all")
    public List<Menu> findAll() {
        return menuService.findAll();
    }


    @PostMapping("/menu/find")
    public List<Menu> findList(@RequestBody Menu cond){
        return menuService.findList(cond);
    }

    @PostMapping("/menu")
    public String insert(@RequestBody Menu menu) {
        menuService.insert(menu);
        return "insert success";
    }

    @PostMapping("/menu/update")
    public String update(@RequestBody Menu menu) {
        menuService.update(menu);
        return "update success";
    }

    @DeleteMapping("/menu/{mid}")
    public String delete(@PathVariable("mid") Long id) {
        menuService.delete(id);
        return "delete success";
    }

}

