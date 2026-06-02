package io.assemblers.project100endgame.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Setter;

public abstract class BaseEntity {
	@Column(name = "created_at", nullable = false, updatable = false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "updated_at", nullable = false)
	protected LocalDateTime updatedAt = LocalDateTime.now();

	@Column(name = "deleted_at")
	protected LocalDateTime deletedAt = null;
}
