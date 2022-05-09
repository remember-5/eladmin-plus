package me.zhengjie.modules.tool.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.generator.domain.GenConfig;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.service.MenuService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.dto.MenuDto;
import me.zhengjie.modules.system.service.dto.MenuQueryCriteria;
import me.zhengjie.modules.system.service.dto.RoleDto;
import me.zhengjie.modules.system.service.mapstruct.MenuMapper;
import me.zhengjie.modules.system.service.mapstruct.RoleMapper;
import me.zhengjie.modules.generator.service.AutoPermissionService;
import me.zhengjie.utils.StringUtils;
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
    private final MenuService menuService;

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    private final MenuMapper menuMapper;
    @Override
    public String genAutoPermission(GenConfig genConfig) {
        String[] menuHeadlines = genConfig.getMenuHeadline().split("/");
        String[] routingAddresss = genConfig.getRoutingAddress().split("/");
        Long pid = null;
        StringBuilder stringBuilder = new StringBuilder();
        boolean isQurey = false;
        List<MenuDto> menuDtosCreates=new ArrayList<>();
        //开始查找 上级
        for (int i = 0; i < menuHeadlines.length-1; i++) {
            MenuQueryCriteria menuQueryCriteria = new MenuQueryCriteria();
            menuQueryCriteria.setBlurry(menuHeadlines[i]);
            List<MenuDto> menuDtos=null;
            isQurey = false;
            try {
                menuDtos = menuService.queryAll(menuQueryCriteria, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (MenuDto menuDto : menuDtos) {
                if (menuDto.getTitle().equals(menuHeadlines[i])){
                    pid= menuDto.getId();
                    isQurey = true;
                    break;
                }
            }

            ///没有搜索到
            if (!isQurey){
                stringBuilder.append("/").append(routingAddresss[i]);
                MenuDto menuDto = create(0, pid, menuHeadlines[i], null, null, stringBuilder.toString());
                menuDtosCreates.add(menuDto);
                pid = menuDto.getId();
            }
        }

        //创建菜单
        String s = lineToHump(genConfig.getTableName());
        if (StringUtils.isNotEmpty(genConfig.getPrefix())) {
            s = StringUtils.toCamelCase(StrUtil.removePrefix(genConfig.getTableName(), genConfig.getPrefix()));
        }
        MenuDto menuDto = create(1,pid,menuHeadlines[menuHeadlines.length-1],
                s+":list",genConfig.getComponentPath(),genConfig.getRoutingAddress());
        menuDtosCreates.add(menuDto);
        //创建按钮
        //添加权限
        MenuDto menuDtoAdd = create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length - 1] + "添加",
                s + ":add", null, null);
        menuDtosCreates.add(menuDtoAdd);
        //修改权限
        MenuDto menuDtoEdit = create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length - 1] + "修改",
                s + ":edit", null, null);
        menuDtosCreates.add(menuDtoEdit);
        //删除权限

        MenuDto menuDtoDel = create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length - 1] + "删除",
                s + ":del", null, null);
        menuDtosCreates.add(menuDtoDel);

        MenuDto menuDtoImportData = create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length - 1] + "导入",
                s + ":importData", null, null);
        menuDtosCreates.add(menuDtoImportData);
        if (Boolean.TRUE.equals(genConfig.getAdminJurisdiction())){
            addMenu(menuDtosCreates);
        }

        return null;
    }

    /**
     * 添加菜单
     * @param menuDtosCreates 添加的菜单列表
     * @return
     */
    private boolean addMenu(List<MenuDto> menuDtosCreates){
        try {
            RoleDto byId = roleService.findById(1L);
            Role role = roleMapper.toEntity(byId);
            for (MenuDto menuDtosCreate : menuDtosCreates) {
                role.getMenus().add(menuMapper.toEntity(menuDtosCreate));
            }
            roleService.updateMenu(role,byId);
        }catch (Exception e){
            log.error("添加菜单错误:{}",e);
            return false;
        }
        return true;
    }
    private MenuDto create(Integer type,Long pid,String title,String permission,String component,String path){
        Menu menu = new Menu();
        menu.setType(type);
        menu.setIFrame(false);
        menu.setCache(false);
        menu.setHidden(false);
        menu.setPid(pid==null?0L:pid);
        menu.setTitle(title);
        menu.setMenuSort(999);
        menu.setPath(path);
        menu.setComponent(component);
        menu.setPermission(permission);
        return menuService.create(menu);
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
