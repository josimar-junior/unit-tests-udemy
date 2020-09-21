package br.com.jj.service;

import static br.com.jj.utils.DateUtils.getDateWithDifferenceOfDays;
import static br.com.jj.utils.DateUtils.isSameDate;
import static br.com.jj.utils.DateUtils.verifyDayOfWeek;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.com.jj.entity.Location;
import br.com.jj.entity.Movie;
import br.com.jj.entity.User;
import br.com.jj.exception.LocationException;
import br.com.jj.exception.MovieWithoutStockException;

public class LocationServiceTest {

	private LocationService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Before
	public void setup() {
		service = new LocationService();
	}
	
	@Test
	public void mustRentMovie() throws Exception {
		
		Assume.assumeFalse(verifyDayOfWeek(LocalDate.now(), DayOfWeek.SATURDAY.getValue()));
		
		// scenario
		User user = new User("User 1");
		
		List<Movie> movies = Arrays.asList(
				new Movie("Movie 1", 2, 5.0),
				new Movie("Movie 2", 2, 5.0)
		);
		
		// action
		Location location = service.rentMovie(user, movies);

		// verification
		error.checkThat(location.getPrice(), is(equalTo(10.0)));
		error.checkThat(isSameDate(location.getLocationDate(), LocalDate.now()), is(true));
		error.checkThat(isSameDate(location.getReturnDate(), getDateWithDifferenceOfDays(1)), is(true));
	}

	@Test(expected = Exception.class)
	public void mustThrowExceptionRentMovie() throws Exception {
		// scenario
		User user = new User("User 1");
		List<Movie> movies = Arrays.asList(new Movie("Movie 1", 0, 5.0));

		// action
		service.rentMovie(user, movies);
	}

	@Test
	public void mustThrowAndCathExceptionRentMovie() {
		// scenario
		User user = new User("User 1");
		List<Movie> movies = Arrays.asList(new Movie("Movie 1", 0, 5.0));

		try {
			// action
			service.rentMovie(user, movies);
			fail("Must throw exception");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Movie without stock"));
		}
	}

	@Test
	public void mustThrowExceptionUsingRuleRentMovie() throws Exception {
		// scenario
		User user = new User("User 1");
		List<Movie> movies = Arrays.asList(new Movie("Movie 1", 0, 5.0));

		// action
		Exception e = assertThrows(Exception.class, () -> service.rentMovie(user, movies));
		assertThat(e.getMessage(), is("Movie without stock"));
	}

	@Test
	public void mustThrowExceptionEmptyUser() throws MovieWithoutStockException {
		// scenario
		List<Movie> movies = Arrays.asList(new Movie("Movie 1", 1, 5.0));

		try {
			service.rentMovie(null, movies);
			fail();
		} catch (LocationException e) {
			assertThat(e.getMessage(), is("Empty user"));
		}
	}

	@Test
	public void mustThrowExceptionEmptyMovie() throws LocationException {
		// scenario
		User user = new User("User 1");

		// action
		LocationException e = assertThrows(LocationException.class, () -> service.rentMovie(user, null));
		assertThat(e.getMessage(), is("Empty movie"));
	}
	
	@Test
	public void mustReturnInTheMondayRentedOnSaturday() throws LocationException, MovieWithoutStockException {
		Assume.assumeTrue(verifyDayOfWeek(LocalDate.now(), DayOfWeek.SATURDAY.getValue()));
		
		// scenario
		User user = new User("User 1");
		List<Movie> movies = Arrays.asList(new Movie("Movie 1", 1, 4.0));

		// action
		Location location = service.rentMovie(user, movies);
		
		boolean isMonday = verifyDayOfWeek(location.getReturnDate(), DayOfWeek.MONDAY.getValue());
		
		assertTrue(isMonday);
	
	}
	
	
}
