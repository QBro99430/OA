package org.quicksand.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.quicksand.model.system.SysMenu;
import org.quicksand.vo.AssginMenuVo;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单树形数据
     */
    List<SysMenu> findNodes();

    /**
     * 根据角色获取授权管线数据
     * @param roleId
     * @return
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    /**
     * 保持角色权限
     * @param assginMenuVo
     */
    void doAssign(AssginMenuVo assginMenuVo);
}
