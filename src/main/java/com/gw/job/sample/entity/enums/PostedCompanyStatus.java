package com.gw.job.sample.entity.enums;

import org.seasar.doma.Domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import java.util.stream.Stream;

/**
 * 応募状況コード
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
@AllArgsConstructor
@Schema(enumAsRef = true, description = "従業員コード一覧: \n" + "* `1` - BEFORE_SELECTION(選考前) \n"
		+ "* `2` - SELECTION(選考中) \n" + "* `3` - ADOPTED(採用) \n" + "* `4` - NOTADOPTED(不採用) \n")
public enum PostedCompanyStatus {

	/**
	 * 選考前
	 */
	BEFORE_SELECTION(1),

	/**
	 * 選考中
	 */
	SELECTION(2),

	/**
	 * 採用
	 */
	ADOPTED(3),

	/**
	 * 不採用
	 */
	NOT＿ADOPTE(4);

	/**
	 * 選考状況コード
	 */
	private final Integer value;

	public Integer getValue() {
		return this.value;
	}

	public static PostedCompanyStatus of(Integer value) {
		if (value == null) {
			throw new IllegalStateException("選考状況コードが設定されていません");
		}
		return Stream.of(values()).filter((v) -> v.getValue().equals(value)).findFirst().orElseThrow(() -> {
			throw new IllegalStateException("異常な選考状況コードが設定されています :" + value);
		});

	}

}
