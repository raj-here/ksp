package com.ksp.service.actionable;

public interface RatingService {
	void list(String referenceId);

	void saveRating(String referenceId, byte rating);

	void delete();
}
