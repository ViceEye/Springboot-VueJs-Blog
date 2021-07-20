package top.venja.controller;

import cn.hutool.core.bean.BeanUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.venja.common.lang.Result;
import top.venja.common.utils.CommonUtil;
import top.venja.entity.Ledger;
import top.venja.service.LedgerService;
import top.venja.util.JwtUtils;
import top.venja.util.ShiroUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Joka.Venja
 * @since 2021-07-13
 */
@RestController
public class LedgerController {

    @Autowired
    LedgerService ledgerService;

    @Autowired
    JwtUtils jwtUtils;

    @RequiresAuthentication
    @PostMapping("/ledger/edit")
    public Result addOneNoteToLedger(@Validated @RequestBody Ledger ledgerDto) {
        Assert.isTrue(CommonUtil.LedgerType.getAllValues().contains(ledgerDto.getType()), "标签类型无效");

        Ledger ledger = new Ledger();
        ledger.setUserId(ShiroUtil.getProfile().getId());
        BeanUtils.copyProperties(ledgerDto, ledger, "");
        ledgerService.saveOrUpdate(ledgerDto);

        return Result.success("成功添加一个账本记录");
    }

    @RequiresAuthentication
    @PostMapping("/ledger/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id, @RequestHeader String authorization) {
        Claims claims = jwtUtils.getClaimByToken(authorization);
        if (claims == null || jwtUtils.isTokenNotValid(claims)) {
            return Result.fail("没有删除权限");
        }

        if (!claims.getSubject().equals("1")) {
            return Result.fail("没有删除权限");
        }
        Ledger ledger = ledgerService.getById(id);
        Assert.notNull(ledger, "该记录不存在或已被删除");

        ledgerService.removeById(id);
        ledgerService.update();

        return Result.success("删除成功");
    }

    @RequiresAuthentication
    @GetMapping("/ledger/{id}")
    public Result getOneNoteFromLedger(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Ledger ledger = ledgerService.getById(id);
        Assert.notNull(ledger, "该记录不存在或已被删除");

        String jwt = request.getHeader("Authorization");
        Claims claims = jwtUtils.getClaimByToken(jwt);
        Ledger falseLedger = new Ledger();
        falseLedger.setAmount(0.0);
        falseLedger.setLabel(1);
        falseLedger.setType(1);
        falseLedger.setNote("你没有权限浏览该记录");
        falseLedger.setComment("你没有权限浏览该记录");

        BeanUtil.copyProperties(ledger, falseLedger, "amount", "label", "type", "note", "comment");

        if (claims == null || jwtUtils.isTokenNotValid(claims)) {
            return Result.success(falseLedger);
        } else {
            if (!claims.getSubject().equals("1")) {
                return Result.success(falseLedger);
            }
        }

        return Result.success(ledger);
    }

}
