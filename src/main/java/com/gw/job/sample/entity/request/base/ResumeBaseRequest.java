package com.gw.job.sample.entity.request.base;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ToString
public abstract class ResumeBaseRequest {

    @Schema(description = "苗字", example = "苗字", required = true)
    private String lastName;

    @Schema(description = "名前", example = "名前", required = true)
    private String firstName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "生年月日 ISO8601拡張形式", type = "string", example = "1990-09-10", required = true)
    private LocalDate birthDate;

    @Schema(description = "職務要約", example = "プログラマーとして働いていました")
    private String jobDescription;
}
