package io.assemblers.project100endgame.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse<T> {

	private boolean success;
	private String msg;
	private T data;

	public static <T> GeneralResponse<T> success(String msg, T data) {
		return GeneralResponse.<T>builder()
			.success(true)
			.msg(msg)
			.data(data)
			.build();
	}

	public static <T> GeneralResponse<T> success(T data) {
		return GeneralResponse.<T>builder()
			.success(true)
			.msg(null)
			.data(data)
			.build();
	}

	public static GeneralResponse<Void> fail(String msg) {
		return GeneralResponse.<Void>builder()
			.success(false)
			.msg(msg)
			.data(null)
			.build();
	}
}
