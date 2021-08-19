package com.wupao.oneclickrelease.controller.permission;

import com.wupao.oneclickrelease.dto.RoleDTO;
import com.wupao.oneclickrelease.entity.ResponseResult;
import com.wupao.oneclickrelease.service.MenuService;
import com.wupao.oneclickrelease.service.RoleService;
import com.wupao.oneclickrelease.vo.permission.MenuNodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuxianglong
 */
@Api(tags = "系统角色接口")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

    private final RoleService roleService;
    private final MenuService menuService;

    @ApiOperation(value = "角色授权")
    @PostMapping("/authority/{id}")
    public ResponseResult authority(@PathVariable Long id, @RequestBody Long[] midList) {
        roleService.authority(id, midList);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "角色菜单")
    @GetMapping("/findRoleMenu/{id}")
    public ResponseResult findRoleMenu(@PathVariable Long id) {
        List<MenuNodeVO> tree = menuService.findMenuTree();
        List<Long> midList = roleService.findMenuIdsByRoleId(id);
        List<Long> ids = menuService.findOpenIds();
        Map<String, Object> map = new HashMap<>(16);
        map.put("tree", tree);
        map.put("midList", midList);
        map.put("open", ids);
        return ResponseResult.ok(map);
    }

    @ApiOperation(value = "角色列表")
    @GetMapping("/findRoleList")
    public ResponseResult findRoleList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize, String roleName) {
        return ResponseResult.ok(roleService.findRoleList(pageNum, pageSize, roleName));
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/add")
    public ResponseResult add(@RequestBody @Validated RoleDTO role) {
        roleService.add(role);
        return ResponseResult.ok();
    }


    @ApiOperation(value = "删除角色", notes = "根据id删除角色信息")
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "根据id查询角色信息", notes = "根据id查询角色信息")
    @GetMapping("/edit/{id}")
    public ResponseResult edit(@PathVariable Long id) {
        return ResponseResult.ok(roleService.findRoleInfo(id));
    }

    @ApiOperation(value = "更新角色", notes = "根据id更新角色信息")
    @PutMapping("/update/{id}")
    public ResponseResult update(@PathVariable Long id, @RequestBody @Validated RoleDTO roleDto) {
        roleService.update(id, roleDto);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "更新状态", notes = "禁用和更新两种状态")
    @PutMapping("/updateStatus/{id}/{status}")
    public ResponseResult updateStatus(@PathVariable Long id, @PathVariable Boolean status) {
        roleService.updateStatus(id, status);
        return ResponseResult.ok();
    }

}
