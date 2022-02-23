package com.crowd.service.Impl;

import com.crowd.dao.MenuMapper;
import com.crowd.entity.Menu;
import com.crowd.service.api.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuMapper menuMapper;

    @Override
    public Menu queryAllMenus() {
        /*
        * 查出所有的菜单，并建立关系
        * */
        List<Menu> menus = menuMapper.queryAllMenu();

        Menu root = null;

        Map<Integer,Menu> dict = new HashMap<Integer,Menu>();

        //找到根节点，并建立id与menu字典
        for (Menu menu:menus) {
            if (menu.getPid()==null){
                root = menu;
            }
            dict.put(menu.getId(),menu);
        }

        //遍历字典，建立menu的父子关系
        for (Menu menu: menus) {
            if (menu.getPid()!=null){
               dict.get(menu.getPid()).getChildren().add(menu);
            }
        }

        return root;
    }

    @Override
    public void addMenu(Menu menu) {
        try {
            menuMapper.insert(menu);
        }catch (Exception e){
            logger.error("出错了："+e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("添加menu出错！");
        }

    }

    @Override
    public void editMenu(Menu menu) {
        try {
            menuMapper.update(menu);
        }catch (Exception e){
            logger.error("出错了："+e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("编辑menu出错！");
        }
    }

    @Override
    public void deleteMenu(Menu menu) {
        try {
            menuMapper.deleteByMenu(menu);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException("删除时出错了！");
        }
    }
}
