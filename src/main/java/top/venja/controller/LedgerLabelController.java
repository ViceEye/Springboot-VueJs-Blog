package top.venja.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import top.venja.common.lang.Result;
import top.venja.common.utils.CommonUtil;
import top.venja.entity.LedgerLabel;
import top.venja.service.LedgerLabelService;
import top.venja.util.JwtUtils;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Joka.Venja
 * @since 2021-07-13
 */
@RestController
public class LedgerLabelController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    LedgerLabelService ledgerLabelService;

    @RequiresAuthentication
    @PostMapping("/newLedgerLabel")
    public Result newLedgerLabel(@Validated @RequestBody LedgerLabel ledgerLabelDto, @Validated @RequestHeader String authorization) {
        LedgerLabel ledgerLabel = ledgerLabelService.getOne(new QueryWrapper<LedgerLabel>().eq("label", ledgerLabelDto.getLabel()));

        Assert.isNull(ledgerLabel, "标签已存在");

        Assert.isTrue(CommonUtil.LedgerType.getAllValues().contains(ledgerLabelDto.getType()), "标签类型无效");

        ledgerLabel = new LedgerLabel();
        BeanUtils.copyProperties(ledgerLabelDto, ledgerLabel, "id");

        ledgerLabelService.saveOrUpdate(ledgerLabel);

        return Result.success(ledgerLabelDto.getLabel(), authorization);
    }

    @RequiresAuthentication
    @PostMapping("/delLedgerLabel")
    public Result delLedgerLabel(@Validated @RequestBody LedgerLabel ledgerLabelDto, @Validated @RequestHeader String authorization) {
        Claims claims = jwtUtils.getClaimByToken(authorization);
        if (claims == null || jwtUtils.isTokenNotValid(claims)) {
            return Result.fail(Result.CODE.LOGIN_TOKEN_FAIL,  "过期Token重新登陆", "Failed");
        }

        if (!claims.getSubject().equals("1")) {
            return Result.fail("权限不足");
        }

        LedgerLabel ledgerLabel = ledgerLabelService.getOne(new QueryWrapper<LedgerLabel>().eq("label", ledgerLabelDto.getLabel()));

        Assert.notNull(ledgerLabel, "标签不存在");

        ledgerLabelService.remove(new QueryWrapper<LedgerLabel>().eq("id", ledgerLabel.getId()));

        return Result.success("成功删除一个标签");
    }

    @RequiresAuthentication
    @GetMapping("/getAllLabel")
    public Result getAllLabel(@RequestParam(defaultValue = "1") Integer type, @Validated @RequestHeader String authorization) {
        Claims claims = jwtUtils.getClaimByToken(authorization);
        if (claims == null || jwtUtils.isTokenNotValid(claims)) {
            return Result.fail(Result.CODE.LOGIN_TOKEN_FAIL,  "过期Token重新登陆", "Failed");
        }

        if (!claims.getSubject().equals("1")) {
            return Result.fail("权限不足");
        }

        List<LedgerLabel> ledgerLabels = ledgerLabelService.list(new QueryWrapper<LedgerLabel>().eq("type", type));

        return Result.success(ledgerLabels);
    }


}
