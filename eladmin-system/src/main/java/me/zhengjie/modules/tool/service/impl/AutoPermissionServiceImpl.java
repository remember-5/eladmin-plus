package me.zhengjie.modules.tool.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.GenConfig;
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
        for (int i = 0; i < menuHeadlines.length-1; i++) {
            MenuQueryCriteria menuQueryCriteria = new MenuQueryCriteria();
            menuQueryCriteria.setBlurry(menuHeadlines[i]);
            List<MenuDto> menuDtos=null;
            try {
                menuDtos = menuService.queryAll(menuQueryCriteria, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (MenuDto menuDto : menuDtos) {
                if (menuDto.getTitle().equals(menuHeadlines[i])){

                }
            }

        }
        return null;
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
