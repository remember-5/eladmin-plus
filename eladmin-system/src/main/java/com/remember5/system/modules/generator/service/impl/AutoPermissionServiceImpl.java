package com.remember5.system.modules.generator.service.impl;

import cn.hutool.core.util.StrUtil;
import com.remember5.core.utils.StringUtils;
import com.remember5.system.modules.generator.domain.GenConfig;
import com.remember5.system.modules.generator.service.AutoPermissionService;
import com.remember5.system.modules.system.domain.Menu;
import com.remember5.system.modules.system.domain.Role;
import com.remember5.system.modules.system.domain.vo.MenuQueryCriteria;
import com.remember5.system.modules.system.service.impl.MenuServiceImpl;
import com.remember5.system.modules.system.service.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangjiahao
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AutoPermissionServiceImpl implements AutoPermissionService {
    private final MenuServiceImpl menuService;
    private final RoleServiceImpl roleService;

    @Override
    public String genAutoPermission(GenConfig genConfig) {
        String[] menuHeadlines = genConfig.getMenuHeadline().split("/");
        String[] routingAddresss = genConfig.getRoutingAddress().split("/");
        Long pid = null;
        StringBuilder stringBuilder = new StringBuilder();
        boolean isQurey = false;
        List<Menu> menuDtosCreates = new ArrayList<>();
        //开始查找 上级
        for (int i = 0; i < menuHeadlines.length - 1; i++) {
            MenuQueryCriteria menuQueryCriteria = new MenuQueryCriteria();
            menuQueryCriteria.setBlurry(menuHeadlines[i]);
            List<Menu> menuDtos = null;
            isQurey = false;
            try {
                menuDtos = menuService.queryAll(menuQueryCriteria, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Menu menu : menuDtos) {
                if (menu.getTitle().equals(menuHeadlines[i])) {
                    pid = menu.getId();
                    isQurey = true;
                    break;
                }
            }

            ///没有搜索到
            if (!isQurey) {
                stringBuilder.append("/").append(routingAddresss[i]);
                Menu menuDto = create(0, pid, menuHeadlines[i], null, null, stringBuilder.toString());
                menuDtosCreates.add(menuDto);
                pid = menuDto.getId();
            }
        }

        //创建菜单
        String s = lineToHump(genConfig.getTableName());
        if (StringUtils.isNotEmpty(genConfig.getPrefix())) {
            s = StringUtils.toCamelCase(StrUtil.removePrefix(genConfig.getTableName(), genConfig.getPrefix()));
        }
        Menu menuDto = create(1, pid, menuHeadlines[menuHeadlines.length - 1],
                s + ":list", genConfig.getComponentPath(), genConfig.getRoutingAddress());
        menuDtosCreates.add(menuDto);
        //创建按钮
        //添加权限
        Menu menuDtoAdd = create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length - 1] + "添加",
                s + ":add", null, null);
        menuDtosCreates.add(menuDtoAdd);
        //修改权限
        Menu menuDtoEdit = create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length - 1] + "修改",
                s + ":edit", null, null);
        menuDtosCreates.add(menuDtoEdit);
        //删除权限

        Menu menuDtoDel = create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length - 1] + "删除",
                s + ":del", null, null);
        menuDtosCreates.add(menuDtoDel);

        Menu menuDtoImportData = create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length - 1] + "导入",
                s + ":importData", null, null);
        menuDtosCreates.add(menuDtoImportData);
        if (Boolean.TRUE.equals(genConfig.getAdminJurisdiction())) {
            addMenu(menuDtosCreates);
        }

        return null;
    }

    /**
     * 添加菜单
     *
     * @param menuDtosCreates 添加的菜单列表
     * @return
     */
    private boolean addMenu(List<Menu> menuDtosCreates) {
        try {
            Role role = roleService.findById(1L);
            for (Menu menu : menuDtosCreates) {
                role.getMenus().add(menu);
            }
            roleService.updateMenu(role);
        } catch (Exception e) {
            log.error("添加菜单错误:{}", e);
            return false;
        }
        return true;
    }

    private Menu create(Integer type, Long pid, String title, String permission, String component, String path) {
        Menu menu = new Menu();
        menu.setType(type);
        menu.setIFrame(false);
        menu.setCache(false);
        menu.setHidden(false);
        menu.setPid(pid == null ? 0L : pid);
        menu.setTitle(title);
        menu.setMenuSort(999);
        menu.setPath(path);
        menu.setComponent(component);
        menu.setPermission(permission);
        menuService.create(menu);
        return menu;
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
