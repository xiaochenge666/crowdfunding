package com.crowd.service.api;

import com.crowd.entity.Menu;

import java.util.List;

public interface MenuService {
    Menu queryAllMenus();

    void addMenu(Menu menu);

    void editMenu(Menu menu);
}
