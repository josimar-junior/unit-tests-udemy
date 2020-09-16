package br.com.jj.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

	private User user;
	private Movie movie;
	private LocalDate locationDate;
	private LocalDate returnDate;
	private Double price;
}
