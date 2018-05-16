package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.dao.MenuDao;
import org.smartloli.kafka.eagle.web.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by weidaping on 2018/4/25.
 */
@Service("menuService")
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    public List<Menu> listAllParentMenu(){
        return menuDao.listAllParentMenu();
    }

    public List<Menu> listAllMenu(){
        List<Menu> parentMenus = menuDao.listAllParentMenu();
        List<Menu> menus = menuDao.listAllMenu();
        for (Menu menu : menus) {
            for (Menu parent : parentMenus) {
                if (parent.getId().equals(menu.getParentId())) {
                    menu.setParentMenu(parent);
                }
            }
        }
        return menus;
    }


    public int saveAddMenu(Menu menu){
        return menuDao.saveAddMenu(menu);
    }


    public Menu findMenuById(int id){
        return menuDao.findMenuById(id);
    }

    public int updateMenuById(Menu menu){
        return menuDao.updateMenuById(menu);
    }

    public void deleteMenuById(int id){
        menuDao.deleteMenuById(id);
    }
}
