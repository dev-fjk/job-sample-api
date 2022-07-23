package com.gw.job.sample.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "社員情報レスポンス")
public class EmployeeResponse {

    @Schema(description = "社員ID", example = "1")
    private long employeeId;

    @Schema(description = "苗字", example = "田中")
    private String lastName;

    @Schema(description = "名前", example = "太郎")
    private String firstName;

    @Schema(description = "入社日", type = "string", example = "2020-10-10")
    private LocalDate entryDate;

    @Schema(description = "部門コード", example = "1")
    private int departmentCode;
}
