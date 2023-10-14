package com.example.runningcoach.exception.custom;

public class NoExistEmailException extends RuntimeException {
	public NoExistEmailException(String message) {
		super(message);
	}
}
