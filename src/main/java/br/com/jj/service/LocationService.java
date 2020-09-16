package br.com.jj.service;

import static br.com.jj.utils.DateUtils.addDays;

import java.time.LocalDate;

import br.com.jj.entity.Location;
import br.com.jj.entity.Movie;
import br.com.jj.entity.User;
import br.com.jj.exception.LocationException;
import br.com.jj.exception.MovieWithoutStockException;

public class LocationService {

	public Location rentMovie(User user, Movie movie) throws LocationException, MovieWithoutStockException {
		
		if(user == null) {
			throw new LocationException("Empty user");
		}
		
		if(movie == null) {
			throw new LocationException("Empty movie");
		}
		
		if(movie.getStock() == 0) {
			throw new MovieWithoutStockException("Movie without stock");
		}
		
		Location location = new Location();
		location.setMovie(movie);
		location.setUser(user);
		location.setLocationDate(LocalDate.now());
		location.setPrice(movie.getLocationPrice());

		//Next day delivery
		LocalDate deliveryDate = LocalDate.now();
		deliveryDate = addDays(deliveryDate, 1);
		location.setReturnDate(deliveryDate);
		
		//Saving a location...	
		//TODO Add save method
		
		return location;
	}
	
}
