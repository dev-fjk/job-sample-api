package com.gw.job.sample.entity.request;


import com.gw.job.sample.entity.request.base.ResumeBaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "レジュメ更新リクエスト")
public class ResumeUpdateRequest extends ResumeBaseRequest {

}
