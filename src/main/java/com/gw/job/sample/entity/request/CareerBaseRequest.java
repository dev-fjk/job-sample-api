package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ToString
@Schema(description = "在籍企業リクエスト")
public abstract class CareerBaseRequest {

    @Schema(description = "企業名", example = "テスト株式会社")
    private String companyName;

    @Schema(description = "在籍中フラグ trueで在籍中", example = "true")
    private boolean belonging;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "入社日 yyyy-MM-ddの形式", example = "2022-04-01")
    private LocalDate entryDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "退職日 yyyy-MM-ddの形式 在籍中の企業には設定出来ない", example = "2022-05-31")
    private LocalDate retiredDate;
}
