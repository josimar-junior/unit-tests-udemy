package br.com.jj.service;

import static br.com.jj.utils.DateUtils.addDays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import br.com.jj.entity.Location;
import br.com.jj.entity.Movie;
import br.com.jj.entity.User;
import br.com.jj.exception.LocationException;
import br.com.jj.exception.MovieWithoutStockException;
import br.com.jj.utils.DateUtils;

public class LocationService {

	public Location rentMovie(User user, List<Movie> movies) throws LocationException, MovieWithoutStockException {
		
		if(user == null) {
			throw new LocationException("Empty user");
		}
		
		if(movies == null || movies.isEmpty()) {
			throw new LocationException("Empty movie");
		}
		
		if(movies.stream().anyMatch(mo -> mo.getStock().equals(0))) {
			throw new MovieWithoutStockException("Movie without stock");
		}
		
		Location location = new Location();
		location.setMovies(movies);
		location.setUser(user);
		location.setLocationDate(LocalDate.now());
		Double totalPrice = 0D;//movies.stream().mapToDouble(Movie::getLocationPrice).sum();
		for(int i = 0; i < movies.size(); i++) {
			totalPrice += getPrice(movies, i);
		}
		location.setPrice(totalPrice);

		//Next day delivery
		LocalDate deliveryDate = LocalDate.now();
		deliveryDate = addDays(deliveryDate, 1);
		if(DateUtils.verifyDayOfWeek(deliveryDate, DayOfWeek.SUNDAY)) {
			deliveryDate = addDays(deliveryDate, 1);
		}
		location.setReturnDate(deliveryDate);
		
		//Saving a location...	
		//TODO Add save method
		
		return location;
	}

	private Double getPrice(List<Movie> movies, int i) {
		Movie movie = movies.get(i);
		Double price = movie.getLocationPrice();
		switch (i) {
			case 2:	price *= 0.75; break;
			case 3:	price *= 0.5; break;
			case 4:	price *= 0.25; break;
			case 5:	price *= 0; break;
		}
		return price;
	}
	
}
