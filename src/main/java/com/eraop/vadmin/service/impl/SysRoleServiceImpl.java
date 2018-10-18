package com.eraop.vadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eraop.vadmin.entity.SysRole;
import com.eraop.vadmin.entity.SysRolePermission;
import com.eraop.vadmin.entity.SysUserRole;
import com.eraop.vadmin.mapper.SysRoleMapper;
import com.eraop.vadmin.service.ISysRolePermissionService;
import com.eraop.vadmin.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eraop.vadmin.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author jason
 * @since 2018-10-18 11:09:17
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private ISysUserRoleService sysUserRoleService;
    @Resource
    private ISysRoleService sysRoleService;


    @Override
    public List<SysRole> getRoles(Integer uid) {
        QueryWrapper<SysUserRole> qw = new QueryWrapper<>();
        qw.eq("uid", uid);
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(qw);
        List<SysRole> roles = (List<SysRole>) sysRoleService.listByIds(sysUserRoles.stream().map(SysUserRole::getRid).collect(Collectors.toList()));
        return roles;
    }
}
