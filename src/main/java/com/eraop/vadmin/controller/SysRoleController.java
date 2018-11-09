package com.eraop.vadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eraop.common.exceptions.NoResourceFoundException;
import com.eraop.common.models.ResponseResult;
import com.eraop.vadmin.entity.SysRole;
import com.eraop.vadmin.service.ISysRoleService;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.rmi.ServerException;
import java.util.List;

/**
 * 前端控制器
 *
 * @author jason
 * @since 2018-10-18 11:09:17
 */
@Api(value = "角色接口", tags = {"角色接口"})
@RestController
@RequestMapping("/api/roles")
public class SysRoleController {
    @Resource
    private ISysRoleService iSysRoleService;

    @ApiOperation(value = "获取角色列表")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<SysRole> list() {
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        return iSysRoleService.list(qw);
    }


    @ApiOperation(value = "获取角色")
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    @RequiresRoles("系统管理员")
    public Object get(@PathVariable(name = "id") long id) throws Exception {
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        qw.eq("id", id);
        SysRole sysRole = iSysRoleService.getOne(qw);
        if (sysRole == null) {
            return ResponseResult.forbidden();
        }
        return ResponseResult.success(sysRole);
    }

    @ApiOperation(value = "删除角色", notes = "HTTP请求使用DELETE")
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") long id) {
    }

}
