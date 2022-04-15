package com.gw.job.sample.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "RFC7807準拠のエラー詳細")
public class ProblemResponse {

    @Schema(description = "問題の要素", example = "リソースが見つかりません")
    String title;

    @Schema(description = "HTTPレスポンスコード", example = "404")
    Integer status;

    @Schema(description = "問題の詳細", example = "指定されたクイズが見つかりません")
    String detail;
}
