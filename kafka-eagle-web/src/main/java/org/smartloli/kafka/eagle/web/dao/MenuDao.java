package org.smartloli.kafka.eagle.web.dao;

import org.apache.ibatis.annotations.Param;
import org.smartloli.kafka.eagle.web.pojo.Menu;

import java.util.List;

/**
 * Created by weidaping on 2018/4/25.
 */
public interface MenuDao {
    /**
     * 列出所有父菜单
     * */
    List<Menu> listAllParentMenu();

    /**
     * 列出所有菜单
     * */
    List<Menu> listAllMenu();

    int saveAddMenu(Menu menu);

    Menu findMenuById(@Param("id") Integer id);

    int updateMenuById(Menu menu);

    void deleteMenuById(@Param("id") Integer id);
}
