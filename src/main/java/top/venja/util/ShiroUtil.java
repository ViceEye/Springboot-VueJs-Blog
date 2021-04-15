package top.venja.util;

import org.apache.shiro.SecurityUtils;
import top.venja.shiro.AccountProfile;

public class ShiroUtil {

    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
