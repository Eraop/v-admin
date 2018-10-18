package com.eraop.vadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eraop.vadmin.entity.SysPermission;
import com.eraop.vadmin.entity.SysRolePermission;
import com.eraop.vadmin.mapper.SysPermissionMapper;
import com.eraop.vadmin.service.ISysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eraop.vadmin.service.ISysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author jason
 * @since 2018-10-18 11:09:17
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {
    @Autowired
    ISysRolePermissionService sysRolePermissionService;
    @Autowired
    ISysPermissionService sysPermissionService;

    @Override
    public List<SysPermission> getPermissionsByRid(Integer rid) {
        QueryWrapper<SysRolePermission> qw = new QueryWrapper<>();
        qw.eq("rid", rid);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionService.list(qw);
        List<SysPermission> permissions = (List<SysPermission>) sysPermissionService
                .listByIds(sysRolePermissions.stream().map(SysRolePermission::getPid).collect(Collectors.toList()));
        return permissions;
    }
}
