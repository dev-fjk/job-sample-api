package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "社員更新リクエスト")
public class EmployeeUpdateRequest extends EmployeeBaseRequest {

    @NotNull
    @Size(max = 30)
    @Schema(description = "更新者", example = "user")
    private String updatedBy;
}
