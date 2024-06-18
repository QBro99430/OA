package org.quicksand.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.quicksand.model.system.SysUser;

public interface SysUserService extends IService<SysUser> {

    /**
     * 更新状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Integer status);
}
