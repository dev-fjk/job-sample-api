package com.gw.job.sample.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 経験職種情報
 */
@Data
@Builder
@Schema(description = "経験職種情報")
public class Occupation {

    @Schema(description = "経験職種ID", example = "1")
    private long occupationId;

    @Schema(description = "職種名", example = "営業")
    private String name;
}
