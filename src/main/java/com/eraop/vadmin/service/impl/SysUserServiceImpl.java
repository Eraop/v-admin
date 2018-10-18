package com.eraop.vadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eraop.vadmin.entity.SysUser;
import com.eraop.vadmin.mapper.SysUserMapper;
import com.eraop.vadmin.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author jason
 * @since 2018-10-18 11:09:17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getUser(String email) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("email",email);
        return getOne(qw);
    }
}
