package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Schema(description = "経験職種リクエスト")
public abstract class OccupationBaseRequest {

    @Schema(description = "職種名", example = "プログラマー")
    private String name;
}
