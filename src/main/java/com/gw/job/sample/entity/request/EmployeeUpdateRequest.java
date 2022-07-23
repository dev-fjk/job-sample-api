package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "社員更新リクエスト")
public class EmployeeUpdateRequest extends EmployeeBaseRequest {
}
