package me.zhengjie.modules.tool.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.GenConfig;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.service.MenuService;
import me.zhengjie.modules.system.service.dto.MenuDto;
import me.zhengjie.modules.system.service.dto.MenuQueryCriteria;
import me.zhengjie.service.AutoPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AutoPermissionServiceImpl implements AutoPermissionService {
    private final MenuService menuService;

    @Override
    public String genAutoPermission(GenConfig genConfig) {
        String[] menuHeadlines = genConfig.getMenuHeadline().split("/");
        String[] routingAddresss = genConfig.getRoutingAddress().split("/");
        Long pid = null;
        StringBuilder stringBuilder = new StringBuilder();
        boolean isQurey = false;
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
                pid = create(0,pid, menuHeadlines[i],null,null,stringBuilder.toString()).getId();
            }
        }
        //创建菜单
        String s = lineToHump(genConfig.getTableName());
        MenuDto menuDto = create(1,pid,menuHeadlines[menuHeadlines.length-1],
                s+":list",genConfig.getPath()+"/index",genConfig.getRoutingAddress());
        //创建按钮
        //添加权限
        create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length-1]+"添加",
                s+":add",null,null);
        //修改权限
        create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length-1]+"修改",
                s+":edit",null,null);
        //删除权限

        create(2, menuDto.getId(), menuHeadlines[menuHeadlines.length-1]+"删除",
                s+":del",null,null);
        return null;
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
