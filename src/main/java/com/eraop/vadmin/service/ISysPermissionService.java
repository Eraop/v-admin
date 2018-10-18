package com.eraop.vadmin.service;

import com.eraop.vadmin.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  服务类
 *
 * @author jason
 * @since 2018-10-18 11:09:17
 */
public interface ISysPermissionService extends IService<SysPermission> {
    List<SysPermission> getPermissionsByRid(Integer rid);

}
