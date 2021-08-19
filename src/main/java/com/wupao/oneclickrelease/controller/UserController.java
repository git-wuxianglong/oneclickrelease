package com.wupao.oneclickrelease.controller;

import com.wupao.oneclickrelease.dto.ModifyPwdAndAvatarDTO;
import com.wupao.oneclickrelease.dto.user.AddUserDTO;
import com.wupao.oneclickrelease.dto.user.UserEditDTO;
import com.wupao.oneclickrelease.dto.user.UserSearchDTO;
import com.wupao.oneclickrelease.entity.ResponseResult;
import com.wupao.oneclickrelease.pojo.Role;
import com.wupao.oneclickrelease.service.RoleService;
import com.wupao.oneclickrelease.service.UserService;
import com.wupao.oneclickrelease.service.RoleConverter;
import com.wupao.oneclickrelease.vo.permission.RoleTransferItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户
 *
 * @author wuxianglong
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Validated
@Api(value = "用户模块", tags = "用户接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @ApiOperation(value = "用户登入", notes = "接收参数用户名和密码,登入成功后,返回JWTToken")
    @PostMapping("/login")
    public ResponseResult login(@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResponseResult.errorMsg("请输入账号和密码");
        }
        return ResponseResult.ok(userService.login(username, password));
    }

    @ApiOperation(value = "用户列表", notes = "模糊查询用户列表")
    @GetMapping("/findUserList")
    @RequiresPermissions({"user:listPage"})
    public ResponseResult findUserList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       UserSearchDTO userSearch) {
        return ResponseResult.ok(userService.findUserList(pageNum, pageSize, userSearch));
    }

    @ApiOperation(value = "用户信息", notes = "用户登入信息")
    @GetMapping("/info")
    public ResponseResult info() {
        return ResponseResult.ok(userService.info());
    }

    @ApiOperation(value = "加载菜单", notes = "用户登入后,根据角色加载菜单树")
    @GetMapping("/findMenu")
    public ResponseResult findMenu() {
        return ResponseResult.ok(userService.findMenu());
    }

    @ApiOperation(value = "分配角色", notes = "角色分配给用户")
    @RequiresPermissions({"user:assign"})
    @PostMapping("/{id}/assignRoles")
    public ResponseResult assignRoles(@PathVariable Long id, @RequestBody Long[] rids) {
        userService.assignRoles(id, rids);
        return ResponseResult.ok();
    }

    @RequiresPermissions({"user:delete"})
    @ApiOperation(value = "删除用户", notes = "删除用户信息，根据用户ID")
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "用户状态", notes = "禁用和启用这两种状态")
    @RequiresPermissions({"user:status"})
    @PutMapping("/updateStatus/{id}/{status}")
    public ResponseResult updateStatus(@PathVariable Long id, @PathVariable Boolean status) {
        userService.updateStatus(id, status);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "更新用户", notes = "更新用户信息")
    @RequiresPermissions({"user:update"})
    @PutMapping("/update/{id}")
    public ResponseResult update(@PathVariable Long id, @RequestBody @Validated UserEditDTO userEdit) {
        log.info("更新员工信息，请求参数id：{}，userEditVO：{}", id, userEdit);
        userEdit.setId(id);
        userService.update(userEdit);
        log.info("更新员工信息，操作结束！");
        return ResponseResult.ok();
    }

    @ApiOperation(value = "编辑用户", notes = "获取用户的详情，编辑用户信息")
    @RequiresPermissions({"user:edit"})
    @GetMapping("/edit/{id}")
    public ResponseResult edit(@PathVariable Long id) {
        return ResponseResult.ok(userService.findUserById(id));
    }

    @ApiOperation(value = "添加用户", notes = "添加用户信息")
    @RequiresPermissions({"user:add"})
    @PostMapping("/add")
    public ResponseResult add(@RequestBody @Validated AddUserDTO addUser) {
        userService.add(addUser);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "已有角色", notes = "根据用户id，获取用户已经拥有的角色")
    @GetMapping("/{id}/roles")
    public ResponseResult roles(@PathVariable Long id) {
        List<Long> values = userService.roles(id);
        List<Role> list = roleService.findAll();
        // 转成前端需要的角色Item
        List<RoleTransferItemVO> items = RoleConverter.converterToRoleTransferItem(list);
        Map<String, Object> map = new HashMap<>(16);
        map.put("roles", items);
        map.put("values", values);
        return ResponseResult.ok(map);
    }

    @ApiOperation(value = "更新用户头像")
    @PutMapping("modifyUserAvatar")
    @ResponseBody
    @RequiresPermissions("user:modifyUserAvatar")
    public ResponseResult modifyUserAvatar(@RequestParam(value = "avatar") String avatar) {
        userService.modifyUserAvatar(avatar);
        return ResponseResult.ok("更新成功");
    }

    @ApiOperation("修改头像和密码")
    @ResponseBody
    @PostMapping("modifyAvatarAndPwd")
    @RequiresPermissions("user:modifyAvatarAndPwd")
    public ResponseResult modifyAvatarAndPwd(@RequestBody ModifyPwdAndAvatarDTO modifyPwdAndAvatar) {
        userService.modifyAvatarAndPwd(modifyPwdAndAvatar);
        return ResponseResult.ok();
    }
}
