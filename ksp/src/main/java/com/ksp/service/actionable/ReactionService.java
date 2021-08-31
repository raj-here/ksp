package com.ksp.service.actionable;

public interface ReactionService {
	void list(String referenceId);

	void saveOrUpdateReaction(String referenceId);

	void delete(String id);
}
