package edu.nuist.ehr.service;

import edu.nuist.ehr.bean.Menu;
import edu.nuist.ehr.bean.Role;
import edu.nuist.ehr.dao.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    public List<Menu> findAll() {
        return menuDao.findAll();
    }

    public List<Menu> findList(Menu cond) {
        return menuDao.findList(cond);
    }

    @Transactional
    public void insert(Menu menu){
        menuDao.insert(menu);
    }

    public void update(Menu menu) {
        menuDao.update(menu);
    }

    public void delete(Long id) {
        menuDao.delete(id);
    }
}
