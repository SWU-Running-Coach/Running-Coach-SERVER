package com.example.runningcoach.exception.custom;

public class FailLoginException extends RuntimeException {
	public FailLoginException(String message) {
		super(message);
	}
}
