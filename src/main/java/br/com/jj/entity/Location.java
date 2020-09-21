package br.com.jj.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

	private User user;
	private List<Movie> movies;
	private LocalDate locationDate;
	private LocalDate returnDate;
	private Double price;
}
