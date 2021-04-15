package top.venja.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.venja.common.dto.LoginDto;
import top.venja.common.dto.RegisterDto;
import top.venja.common.lang.Result;
import top.venja.entity.User;
import top.venja.service.UserService;
import top.venja.shiro.JwtToken;
import top.venja.util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Joka.Venja
 * @since 2021-02-22
 */
@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "账号或密码错误");

        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("账号或密码错误");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        JwtToken token = new JwtToken(jwt);
        SecurityUtils.getSubject().login(token);

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-expose-Headers", "Authorization");

        return Result.success(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @PostMapping("/remember")
    public Result rememberMe(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        Claims claims = jwtUtils.getClaimByToken(jwt);
        if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
            SecurityUtils.getSubject().logout();
        }

        return Result.success("");
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

    @PostMapping("/register")
    public Result register(@Validated @RequestBody RegisterDto registerDto, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", registerDto.getUsername()));

        Assert.isNull(user, "账号已存在");

        if (!registerDto.getPassword().equals(registerDto.getRepeatPassword())) {
            return Result.fail("确认密码与密码不一致");
        }

        user = new User();
        user.setUsername(registerDto.getUsername());
        user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        user.setPassword(SecureUtil.md5(registerDto.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setStatus(0);

        userService.saveOrUpdate(user);

        user = userService.getOne(new QueryWrapper<User>().eq("username", registerDto.getUsername()));
        Assert.notNull(user, "账号注册失败");

        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-expose-Headers", "Authorization");

        return Result.success(
                MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

}
