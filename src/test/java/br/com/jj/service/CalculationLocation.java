package br.com.jj.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.com.jj.entity.Location;
import br.com.jj.entity.Movie;
import br.com.jj.entity.User;
import br.com.jj.exception.LocationException;
import br.com.jj.exception.MovieWithoutStockException;

@RunWith(Parameterized.class)
public class CalculationLocation {
	
	@Parameter
	public List<Movie> movies;
	
	@Parameter(value = 1)
	public Double price;
	
	@Parameter(value = 2)
	public String scenario;
	
	public LocationService service;
	
	@Before
	public void setup() {
		service = new LocationService();
	}

	private static Movie movie1 = new Movie("Movie 1", 2, 4.0);
	private static Movie movie2 = new Movie("Movie 2", 2, 4.0);
	private static Movie movie3 = new Movie("Movie 3", 2, 4.0);
	private static Movie movie4 = new Movie("Movie 4", 2, 4.0);
	private static Movie movie5 = new Movie("Movie 5", 2, 4.0);
	private static Movie movie6 = new Movie("Movie 6", 2, 4.0);
	private static Movie movie7 = new Movie("Movie 7", 2, 4.0);
	
	@Parameters(name="{2}")
	public static Collection<Object[]> getProperties() {
		return Arrays.asList(new Object[][] {
			{Arrays.asList(movie1, movie2), 8.0, "2 movies: No discount"},
			{Arrays.asList(movie1, movie2, movie3), 11.0, "3 movies: 25%"},
			{Arrays.asList(movie1, movie2, movie3, movie4), 13.0, "4 movies: 50%"},
			{Arrays.asList(movie1, movie2, movie3, movie4, movie5), 14.0, "5 movies: 75%"},
			{Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6), 14.0, "6 movies: 100%"},
			{Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7), 18.0, "7 movies: No discount"}
		});
	}
	
	@Test
	public void mustCalculationLocationWithDiscount() throws LocationException, MovieWithoutStockException {
		// scenario
		User user = new User("User 1");

		// action
		Location location = service.rentMovie(user, movies);

		// verification
		assertThat(location.getPrice(), is(price));
	}
}
