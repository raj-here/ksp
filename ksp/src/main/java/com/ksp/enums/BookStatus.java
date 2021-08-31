package com.ksp.enums;

public enum BookStatus {
	NONE("Don't want to share this book"), EXCHANGE("Want to exchnage this book"),
	RENT("Want to rent this book"), SELL("Want to sell book"), DONATE("Want to donate this book");
	private String value;

	public String getValue() {
		return value;
	}

	BookStatus(String value) {
		this.value = value;

	}
}
