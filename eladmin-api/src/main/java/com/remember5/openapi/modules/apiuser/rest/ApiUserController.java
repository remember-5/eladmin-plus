package com.remember5.openapi.modules.apiuser.rest;

import com.remember5.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiahao
 */
@Slf4j
@Tag(name = "API用户管理")
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class ApiUserController {

    @Operation(summary = "获取用户信息")
    @GetMapping(value = "/info")
    public R<?> getUserInfo() {
        return R.success();
    }

    @Operation(summary = "获取用户信息")
    @PostMapping(value = "/all")
    public R<?> all() {
        return R.success();
    }

}
