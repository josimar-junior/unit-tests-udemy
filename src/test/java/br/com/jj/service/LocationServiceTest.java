package br.com.jj.service;

import static br.com.jj.utils.DateUtils.getDateWithDifferenceOfDays;
import static br.com.jj.utils.DateUtils.isSameDate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.com.jj.entity.Location;
import br.com.jj.entity.Movie;
import br.com.jj.entity.User;
import br.com.jj.exception.LocationException;
import br.com.jj.exception.MovieWithoutStockException;

public class LocationServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Test
	public void mustRentMovie() throws Exception {
		// scenario
		LocationService service = new LocationService();
		User user = new User("User 1");
		Movie movie = new Movie("Movie 1", 2, 5.0);

		// action
		Location location = service.rentMovie(user, movie);

		// verification
		error.checkThat(location.getPrice(), is(equalTo(5.0)));
		error.checkThat(isSameDate(location.getLocationDate(), LocalDate.now()), is(true));
		error.checkThat(isSameDate(location.getReturnDate(), getDateWithDifferenceOfDays(1)), is(true));
	}

	@Test(expected = Exception.class)
	public void mustThrowExceptionRentMovie() throws Exception {
		// scenario
		LocationService service = new LocationService();
		User user = new User("User 1");
		Movie movie = new Movie("Movie 1", 0, 5.0);

		// action
		service.rentMovie(user, movie);
	}

	@Test
	public void mustThrowAndCathExceptionRentMovie() {
		// scenario
		LocationService service = new LocationService();
		User user = new User("User 1");
		Movie movie = new Movie("Movie 1", 0, 5.0);

		try {
			// action
			service.rentMovie(user, movie);
			fail("Must throw exception");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Movie without stock"));
		}
	}

	@Test
	public void mustThrowExceptionUsingRuleRentMovie() throws Exception {
		// scenario
		LocationService service = new LocationService();
		User user = new User("User 1");
		Movie movie = new Movie("Movie 1", 0, 5.0);

		// action
		Exception e = assertThrows(Exception.class, () -> service.rentMovie(user, movie));
		assertThat(e.getMessage(), is("Movie without stock"));
	}

	@Test
	public void mustThrowExceptionEmptyUser() throws MovieWithoutStockException {
		// scenario
		LocationService service = new LocationService();
		Movie movie = new Movie("Movie 1", 1, 5.0);

		try {
			service.rentMovie(null, movie);
			fail();
		} catch (LocationException e) {
			assertThat(e.getMessage(), is("Empty user"));
		}
	}

	@Test
	public void mustThrowExceptionEmptyMovie() throws LocationException {
		// scenario
		LocationService service = new LocationService();
		User user = new User("User 1");

		// action
		LocationException e = assertThrows(LocationException.class, () -> service.rentMovie(user, null));
		assertThat(e.getMessage(), is("Empty movie"));
	}
}
