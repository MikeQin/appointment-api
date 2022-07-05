package com.appointment.entity;

@SuppressWarnings("ALL")
public class PersistentException extends RuntimeException {

	private static final long serialVersionUID = -5606094713330396599L;

	public PersistentException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistentException(String message) {
		super(message);
	}

	public PersistentException(Throwable cause) {
		super(cause);
	}

	
}
