package br.com.jj.builder;

import br.com.jj.entity.Movie;

public class MovieBuilder {

	private Movie movie;
	
	private MovieBuilder() {}
	
	public static MovieBuilder initMovie() {
		MovieBuilder builder = new MovieBuilder();
		builder.movie = new Movie();
		builder.movie.setName("Movie 1");
		builder.movie.setStock(2);
		builder.movie.setLocationPrice(4.0);
		return builder;
	}
	
	public MovieBuilder withoutStock() {
		this.movie.setStock(0);
		return this;
	}
	
	public MovieBuilder withPrice(Double price) {
		this.movie.setLocationPrice(price);
		return this;
	}
	
	public Movie builder() {
		return movie;
	}
}
