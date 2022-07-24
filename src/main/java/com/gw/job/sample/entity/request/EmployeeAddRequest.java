package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "社員追加リクエスト")
public class EmployeeAddRequest extends EmployeeBaseRequest {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "入社日", type = "string", example = "2020-10-10")
    private LocalDate entryDate;

    @NotNull
    @Size(max = 30)
    @Schema(description = "作成者", example = "user")
    private String createdBy;
}
