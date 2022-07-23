package com.gw.job.sample.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * 在籍企業情報
 */
@Data
@Builder
@Schema(description = "在籍企業情報")
public class Career {

    @Schema(description = "在籍企業ID", example = "1")
    private long experienceId;

    @Schema(description = "企業名", example = "テスト株式会社")
    private String companyName;

    @Schema(description = "在籍中フラグ", example = "true")
    private boolean belonging;

    @Schema(description = "入社日", example = "2022-04-01")
    private LocalDate entryDate;

    @Schema(description = "退職日", example = "2022-05-31")
    private LocalDate retiredDate;

    private List<Occupation> occupations;
}
