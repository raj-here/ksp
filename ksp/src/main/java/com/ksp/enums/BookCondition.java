package com.ksp.enums;

public enum BookCondition {
	ALMOST_NEW_BOOK("Almost new book"), NOTICEABLY_USED("Noticeably used"),
	OLD_BOOK("The pages are holding together(Old book)");
	private String value;

	public String getValue() {
		return value;
	}

	BookCondition(String value) {
		this.value = value;

	}
}
