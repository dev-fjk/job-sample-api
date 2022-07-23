package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Schema(description = "社員リクエスト")
public abstract class EmployeeBaseRequest {

    @Schema(description = "苗字", example = "苗字")
    private String lastName;

    @Schema(description = "名前", example = "名前")
    private String firstName;

    @Schema(description = "部門コード", example = "2020-10-10")
    private int departmentCode;
}
