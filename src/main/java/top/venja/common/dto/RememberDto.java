package top.venja.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class RememberDto implements Serializable {

    @NotEmpty(message = "Token不能为空")
    private String token;

}
