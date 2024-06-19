package org.quicksand.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quicksand.model.system.SysRoleMenu;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
}
