package org.quicksand.model.system.helper;

import org.quicksand.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 根据菜单数据构建菜单数据
 * @Author: quicksand
 * @Version: 1.0
 */
public class MenuHelper {

    /**
     * 使用递归方法构建菜单
     * @param sysMenusList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenusList) {
        List<SysMenu> trees = new ArrayList<>();
        for (SysMenu sysMenu : sysMenusList) {
            if (sysMenu.getParentId().longValue() == 0){
                trees.add(findChildren(sysMenu,sysMenusList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param sysMenu
     * @param treeNodes
     * @return
     */
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {

        sysMenu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu it : treeNodes) {
            if (sysMenu.getId().longValue() == it.getParentId().longValue()){
                if (sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return sysMenu;
    }

}
