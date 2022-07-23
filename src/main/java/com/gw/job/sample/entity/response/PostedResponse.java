package com.gw.job.sample.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@Schema(description = "応募情報レスポンス")
public class PostedResponse {

    @Schema(description = "企業ID", example = "1")
    private long companyId;

    @Schema(description = "ユーザーID", example = "1")
    private long userId;

    @Schema(description = "選考状況", example = "1")
    private int status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "入社日", example = "2022-07-15")
    private LocalDate entryDate;
}
