package com.gw.job.sample.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.seasar.doma.Domain;

/**
 * 従業員コード
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
@AllArgsConstructor
@Schema(enumAsRef = true, description = "従業員コード一覧: \n"
        + "* `1` - OFFICER(役員) \n"
        + "* `2` - AFFAIRS(総務部) \n"
        + "* `3` - ACCOUNTS(経理部) \n"
        + "* `4` - SALES(営業部) \n"
        + "* `5` - DEVELOPERS(開発部) \n"
)
public enum DepartmentCode {

    /**
     * 役員
     */
    OFFICER(1),

    /**
     * 総務部
     */
    AFFAIRS(2),

    /**
     * 経理部
     */
    ACCOUNTS(3),

    /**
     * 営業部
     */
    SALES(4),

    /**
     * 開発部
     */
    DEVELOPERS(5);

    /**
     * 部門コード
     */
    private final Integer value;

    public Integer getValue() {
        return this.value;
    }

    /**
     * 数値からDepartmentCodeを作成する
     * Domaで DBからJavaObjectへの変換の際にも使用される
     *
     * @param value DBの値
     * @return {@link DepartmentCode} 従業員コード
     *
     * @throws IllegalStateException 部門コードが未設定 or 異常値の場合にthrowする
     */
    public static DepartmentCode of(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("部門コードが設定されていません");
        }
        return Stream.of(values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("異常な部門コードが設定されています :" + value);
                });
    }
}
