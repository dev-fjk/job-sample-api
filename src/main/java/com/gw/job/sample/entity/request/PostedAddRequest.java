package com.gw.job.sample.entity.request;

import lombok.Data;

import javax.validation.constraints.Size;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "応募情報追加リクエスト")
public class PostedAddRequest {
    @NotNull
    @Size(max = 30)
    @Schema(description = "作成者", example = "manual")
    private String createdBy;
}
