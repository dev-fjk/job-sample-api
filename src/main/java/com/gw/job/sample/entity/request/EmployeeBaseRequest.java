package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Data
@ToString
@Schema(description = "社員リクエスト")
public abstract class EmployeeBaseRequest {

    @NotEmpty
    @Size(max = 15)
    @Schema(description = "苗字", example = "苗字")
    private String lastName;

    @NotEmpty
    @Size(max = 15)
    @Schema(description = "名前", example = "名前")
    private String firstName;

    @NotNull
    @Range(min = 1, max = 5)
    @Schema(description = "部門コード 1から5の範囲で設定", example = "1")
    private Integer departmentCode;
}
