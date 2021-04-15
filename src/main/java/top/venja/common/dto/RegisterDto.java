package top.venja.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class RegisterDto implements Serializable {

    @NotEmpty(message = "昵称不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "确认密码不能为空")
    private String repeatPassword;
}
