package org.smartloli.kafka.eagle.web.dao;

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
}
