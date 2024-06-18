package org.quicksand.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quicksand.auth.service.SysRoleService;
import org.quicksand.common.result.Result;
import org.quicksand.vo.AssginRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description: 角色管理
 * @Author: quicksand
 * @Version: 1.0
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = sysRoleService.findRoleByAdminId(userId);
        return Result.ok(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }


}
