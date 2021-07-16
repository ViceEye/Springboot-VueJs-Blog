package top.venja.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LedgerLabelDto {

    @NotEmpty(message = "标签不能为空")
    private String label;

    private Integer type;

}
