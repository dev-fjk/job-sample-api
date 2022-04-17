package com.gw.job.sample.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "応募判定レスポンス")
public class PostedResponse {

    @Schema(description = "ユーザーID", example = "1")
    private long userId;

    @Schema(description = "企業ID", example = "1")
    private long cid;

    @Schema(description = "応募済み判定 応募済みの場合 trueを返す", example = "false")
    private boolean posted;
}
