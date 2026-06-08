package io.assemblers.project100endgame.common.response;

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
	private String message;
	private T data;

	public static <T> GeneralResponse<T> success(String msg, T data) {
		return GeneralResponse.<T>builder()
			.success(true)
			.message(msg)
			.data(data)
			.build();
	}

	public static <T> GeneralResponse<T> success(T data) {
		return GeneralResponse.<T>builder()
			.success(true)
			.message(null)
			.data(data)
			.build();
	}

	public static GeneralResponse<Void> fail(String msg) {
		return GeneralResponse.<Void>builder()
			.success(false)
			.message(msg)
			.data(null)
			.build();
	}
}
