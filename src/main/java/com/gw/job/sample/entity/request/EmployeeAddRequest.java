package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "社員追加リクエスト")
public class EmployeeAddRequest extends EmployeeBaseRequest {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "入社日", type = "string", example = "2020-10-10")
    private LocalDate entryDate;
}
