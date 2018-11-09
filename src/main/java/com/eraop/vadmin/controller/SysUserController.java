package com.eraop.vadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eraop.common.exceptions.NoResourceFoundException;
import com.eraop.common.models.ResponseResult;
import com.eraop.vadmin.entity.SysUser;
import com.eraop.vadmin.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 前端控制器
 *
 * @author jason
 * @since 2018-10-18 11:09:17
 */
@Api(value = "用户接口", tags = {"用户接口"})
@RestController
@RequestMapping("/api/users")
public class SysUserController {
    @Resource
    private ISysUserService sysUserService;

    @ApiOperation(value = "获取用户列表")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<SysUser> list() {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        return sysUserService.list(qw);
    }

    @ApiOperation(value = "获取用户")
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable(name = "id") long id) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("id", id);
        SysUser sysUser = sysUserService.getOne(qw);
        if (sysUser == null) {
            return ResponseResult.notFound();
        }
        return ResponseResult.success(sysUser);
    }

    @ApiOperation(value = "删除用户", notes = "HTTP请求使用DELETE")
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") long id) {
    }

}
