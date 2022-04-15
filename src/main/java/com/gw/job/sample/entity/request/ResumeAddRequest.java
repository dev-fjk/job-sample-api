package com.gw.job.sample.entity.request;


import com.gw.job.sample.entity.request.base.ResumeBaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "レジュメ追加リクエスト")
public class ResumeAddRequest extends ResumeBaseRequest {

}
