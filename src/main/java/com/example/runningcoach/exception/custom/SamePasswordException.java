package com.example.runningcoach.exception.custom;

public class SamePasswordException extends RuntimeException {
	public SamePasswordException(String message) {
		super(message);
	}
}
