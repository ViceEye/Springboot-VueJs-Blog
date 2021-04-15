package top.venja.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import top.venja.common.lang.Result;
import top.venja.entity.Blog;
import top.venja.service.BlogService;
import top.venja.util.JwtUtils;
import top.venja.util.ShiroUtil;

import javax.servlet.ServletRequest;
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
public class BlogController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage, HttpServletRequest request) {
        IPage<Blog> pageData;

        String jwt = request.getHeader("Authorization");
        Claims claims = jwtUtils.getClaimByToken(jwt);
        if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
            SecurityUtils.getSubject().logout();
            System.out.println("logout");
        }
        System.out.println(jwt);

        if (SecurityUtils.getSubject().isAuthenticated()) {
            Page<Blog> page = new Page<>(currentPage, 7);
            pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        } else {
            Page<Blog> page = new Page<>(currentPage, 7);
            pageData = blogService.page(page, new QueryWrapper<Blog>().eq("type", 0).orderByDesc("created"));
        }

        return Result.success(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客不存在或已被删除");

        return Result.success(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {

        Blog temp = null;

        // 编辑状态
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "你没有权限编辑此文章");

        //添加状态
        } else {

            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
            temp.setType(1);

        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status", "type");
        blogService.saveOrUpdate(temp);

        return Result.success(null);
    }

    @RequiresAuthentication
    @PostMapping("/blog/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客不存在或已被删除");

        blogService.removeById(id);
        blogService.update();

        return Result.success("删除成功");
    }

    @PutMapping("/upload")
    public Result uploadImg() {
        return Result.success("上传成功");
    }


}
