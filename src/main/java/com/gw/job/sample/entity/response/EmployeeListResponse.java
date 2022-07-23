package com.gw.job.sample.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "社員一覧レスポンス")
public class EmployeeListResponse {

    @Schema(description = "総件数", example = "100")
    private int total;

    @Schema(description = "取得開始位置", example = "1")
    private int start;

    @Schema(description = "取得件数", example = "20")
    private int count;

    List<Employee> employees;

    @Data
    @Builder
    @Schema(description = "社員情報")
    public static class Employee {

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
}
