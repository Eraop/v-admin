package com.eraop.vadmin.service;

import com.eraop.vadmin.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *  服务类
 *
 * @author jason
 * @since 2018-10-18 11:09:17
 */
public interface ISysUserService extends IService<SysUser> {
SysUser getUser(String email);
}
