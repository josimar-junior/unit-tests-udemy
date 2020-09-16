package br.com.jj.exception;

public class MovieWithoutStockException extends Exception {

	private static final long serialVersionUID = 1L;

	public MovieWithoutStockException(String message) {
		super(message);
	}
}
