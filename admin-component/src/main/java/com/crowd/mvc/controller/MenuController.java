package com.crowd.mvc.controller;


import com.crowd.entity.Menu;
import com.crowd.service.api.MenuService;
import com.crowd.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @RequestMapping("/queryAll")
    public ResponseEntity<Menu> queryMenu(){
        //查询菜单
        Menu menus = menuService.queryAllMenus();
        return ResponseEntity.successWithData(menus);
    }

    @RequestMapping("/do/add")
    public ResponseEntity addMenu(@RequestBody Menu menu){
        menuService.addMenu(menu);
        return ResponseEntity.successWithoutData();
    }

    @RequestMapping("/do/edit")
    public ResponseEntity editMenu(@RequestBody Menu menu){
        menuService.editMenu(menu);
        return ResponseEntity.successWithoutData();
    }

    @RequestMapping("/do/delete")
    public ResponseEntity deleteMenu(@RequestBody Menu menu){
        menuService.deleteMenu(menu);
        return ResponseEntity.successWithoutData();
    }
}
