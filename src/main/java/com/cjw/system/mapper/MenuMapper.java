package com.cjw.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjw.system.model.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface MenuMapper extends BaseMapper<Menu> {
    @Select("select distinct menu.menu_id," +
            "   menu.parent_id," +
            "   menu.menu_name," +
            "   menu.url," +
            "   menu.perms," +
            "   menu.order_num," +
            "   menu.icon " +
            "   from    user," +
            "           role," +
            "           user_role," +
            "           menu," +
            "           role_menu " +
            "        where user.user_id = user_role.user_id " +
            "          and role.role_id = user_role.role_id " +
            "          and role.role_id = role_menu.role_id " +
            "          and menu.menu_id = role_menu.menu_id " +
            "          and user.username = #{username}")
    @ResultMap(value = "com.cjw.system.mapper.MenuMapper.BaseResultMap")
    List<Menu> selectMenuByUserName(@Param("username") String username);

    List<Menu> selectAllTree();

    /*
     * 查询父级菜单下的所以子集菜单
     */
    @Select(" select menu_id, parent_id, menu_name, url, perms, order_num, create_time, modify_time, icon from menu where parent_id = #{parentId} order by order_num")
    List<Menu> selectByParentId(Integer parentId);

    @Select("select ifnull(max(order_num),0) from menu")
    Integer selectMaxOrderNum();

    Integer swap(Integer currentId, Integer swapId);

    List<Menu> selectAllMenuAndCountOperator();

    @Delete("delete from menu where menu_id = #{menuId} or parent_id = #{menuId}")
    Integer deleteMenu(@Param("menuId") Integer menuId);
}