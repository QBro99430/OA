package org.quicksand.auth.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quicksand.auth.mapper.SysUserMapper;
import org.quicksand.auth.service.SysUserService;
import org.quicksand.model.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: quicksand
 * @Version: 1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Transactional
    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = this.getById(id);
        if (status.intValue() == 1){
            sysUser.setStatus(status);
        }else {
            sysUser.setStatus(0);
        }

        this.updateById(sysUser);
    }
}
