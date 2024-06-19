package org.quicksand.auth.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quicksand.auth.mapper.SysMenuMapper;
import org.quicksand.auth.mapper.SysRoleMenuMapper;
import org.quicksand.auth.mapper.SysUserMapper;
import org.quicksand.auth.service.SysMenuService;
import org.quicksand.common.execption.OaException;
import org.quicksand.model.system.SysMenu;
import org.quicksand.model.system.SysRoleMenu;
import org.quicksand.model.system.helper.MenuHelper;
import org.quicksand.vo.AssginMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: quicksand
 * @Version: 1.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    /**
     * 菜单树形数据
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {
        //全部权限列表
        List<SysMenu> sysMenuList = this.list();
        if (StringUtils.isEmpty(sysMenuList)) return null;

        //构建树形数据
        List<SysMenu> result = MenuHelper.buildTree(sysMenuList);

        return result;
    }



    @Override
    public boolean removeById(Serializable id) {
        int count = this.count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id));
        if (count > 0){
            throw new OaException(201, "菜单不能删除");
        }
        sysUserMapper.deleteById(id);
        return false;
    }

    /**
     * 根据角色获取授权管线数据
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //获取权限列表
        List<SysMenu> allSysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));

        //根据角色id获取角色权限
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, roleId));

        //转换给角色id与角色权限对应的map对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(e -> e.getMenuId()).collect(Collectors.toList());

        allSysMenuList.forEach(permission ->{
            if (menuIdList.contains(permission.getId())){
                permission.setSelect(true);
            }else {
                permission.setSelect(false);
            }
        });

        List<SysMenu> sysMenuList = MenuHelper.buildTree(allSysMenuList);

        return sysMenuList;
    }

    /**
     * 保持角色权限
     * @param assginMenuVo
     */
    @Transactional
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {

        //删除角色之前分配的菜单
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, assginMenuVo.getRoleId()));

        //添加最新菜单
        for (Long menuId : assginMenuVo.getMenuIdList()) {
            if (StringUtils.isEmpty(menuId)) continue;
            SysRoleMenu rolePermission = new SysRoleMenu();
            rolePermission.setRoleId(assginMenuVo.getRoleId());
            rolePermission.setMenuId(menuId);
            sysRoleMenuMapper.insert(rolePermission);
        }
    }
}
