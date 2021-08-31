package com.ksp.service.actionable;

public interface CommentService {
	void list(String referenceId);

	void saveComment(String referenceId);

	void delete();
}
