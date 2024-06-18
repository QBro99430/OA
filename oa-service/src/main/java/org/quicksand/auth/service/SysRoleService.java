package org.quicksand.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.quicksand.model.system.SysRole;
import org.quicksand.vo.AssginRoleVo;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据用户获取角色数据
     * @param userId
     * @return
     */
    Map<String, Object> findRoleByAdminId(Long userId);

    /**
     * 分配角色
     * @param assginRoleVo
     */
    void doAssign(AssginRoleVo assginRoleVo);
}
