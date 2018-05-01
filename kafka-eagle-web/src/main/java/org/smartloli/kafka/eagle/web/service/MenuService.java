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


}
