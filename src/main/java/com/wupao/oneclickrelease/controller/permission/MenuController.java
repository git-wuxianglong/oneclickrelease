package com.wupao.oneclickrelease.controller.permission;

import com.wupao.oneclickrelease.dto.MenuDTO;
import com.wupao.oneclickrelease.entity.ResponseResult;
import com.wupao.oneclickrelease.pojo.Menu;
import com.wupao.oneclickrelease.service.MenuService;
import com.wupao.oneclickrelease.vo.permission.MenuNodeVO;
import com.wupao.oneclickrelease.vo.permission.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单权限
 *
 * @author wuxianglong
 */
@Api(tags = "菜单权限接口")
@RequestMapping("/menu")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuController {

    private final MenuService menuService;

    @ApiOperation(value = "加载菜单树", notes = "获取所有菜单树，以及展开项")
    @GetMapping("/tree")
    @RequiresPermissions({"menu:tree"})
    public ResponseResult tree() {
        List<MenuNodeVO> menuTree = menuService.findMenuTree();
        List<Long> ids = menuService.findOpenIds();
        Map<String, Object> map = new HashMap<>(16);
        map.put("tree", menuTree);
        map.put("open", ids);
        return ResponseResult.ok(map);
    }

    @ApiOperation(value = "新增菜单")
    @RequiresPermissions({"menu:add"})
    @PostMapping("/add")
    public ResponseResult add(@RequestBody @Validated MenuDTO menuDto) {
        Menu node = menuService.add(menuDto);
        Map<String, Object> map = new HashMap<>(16);
        map.put("id", node.getId());
        map.put("menuName", node.getMenuName());
        map.put("children", new ArrayList<>());
        map.put("icon", node.getIcon());
        return ResponseResult.ok(map);
    }

    @ApiOperation(value = "删除菜单", notes = "根据id删除菜单节点")
    @RequiresPermissions({"menu:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "菜单详情", notes = "根据id编辑菜单，获取菜单详情")
    @RequiresPermissions({"menu:edit"})
    @GetMapping("/edit/{id}")
    public ResponseResult edit(@PathVariable Long id) {
        return ResponseResult.ok(menuService.edit(id));
    }

    @ApiOperation(value = "更新菜单", notes = "根据id更新菜单节点")
    @RequiresPermissions({"menu:update"})
    @PutMapping("/update/{id}")
    public ResponseResult update(@PathVariable Long id, @RequestBody @Validated MenuVO menuVO) {
        menuService.update(id, menuVO);
        return ResponseResult.ok();
    }
}
