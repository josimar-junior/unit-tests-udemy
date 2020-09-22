package br.com.jj.service;

import static br.com.jj.builder.MovieBuilder.initMovie;
import static br.com.jj.builder.UserBuilder.initUser;
import static br.com.jj.matcher.OwnMatcher.fallsOnMonday;
import static br.com.jj.matcher.OwnMatcher.isToday;
import static br.com.jj.matcher.OwnMatcher.isTodayWithDifferenceDays;
import static br.com.jj.utils.DateUtils.verifyDayOfWeek;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
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
		
		Assume.assumeFalse(verifyDayOfWeek(LocalDate.now(), DayOfWeek.SATURDAY));
		
		// scenario
		User user = initUser().builder();
		
		List<Movie> movies = Arrays.asList(
				initMovie().withPrice(5.0).builder(),
				initMovie().withPrice(5.0).builder()
			);
		
		// action
		Location location = service.rentMovie(user, movies);

		// verification
		error.checkThat(location.getPrice(), is(equalTo(10.0)));
		//error.checkThat(isSameDate(location.getLocationDate(), LocalDate.now()), is(true));
		error.checkThat(location.getLocationDate(), isToday());
		//error.checkThat(isSameDate(location.getReturnDate(), getDateWithDifferenceOfDays(1)), is(true));
		error.checkThat(location.getReturnDate(), isTodayWithDifferenceDays(1));
	}

	@Test(expected = Exception.class)
	public void mustThrowExceptionRentMovie() throws Exception {
		// scenario
		User user = initUser().builder();
		List<Movie> movies = Arrays.asList(initMovie().withoutStock().builder());

		// action
		service.rentMovie(user, movies);
	}

	@Test
	public void mustThrowAndCathExceptionRentMovie() {
		// scenario
		User user = initUser().builder();
		List<Movie> movies = Arrays.asList(initMovie().withoutStock().builder());

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
		User user = initUser().builder();
		List<Movie> movies = Arrays.asList(initMovie().withoutStock().builder());

		// action
		Exception e = assertThrows(Exception.class, () -> service.rentMovie(user, movies));
		assertThat(e.getMessage(), is("Movie without stock"));
	}

	@Test
	public void mustThrowExceptionEmptyUser() throws MovieWithoutStockException {
		// scenario
		List<Movie> movies = Arrays.asList(initMovie().builder());

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
		User user = initUser().builder();

		// action
		LocationException e = assertThrows(LocationException.class, () -> service.rentMovie(user, null));
		assertThat(e.getMessage(), is("Empty movie"));
	}
	
	@Test
	public void mustReturnInTheMondayRentedOnSaturday() throws LocationException, MovieWithoutStockException {
		Assume.assumeTrue(verifyDayOfWeek(LocalDate.now(), DayOfWeek.SATURDAY));
		
		// scenario
		User user = initUser().builder();
		List<Movie> movies = Arrays.asList(initMovie().withoutStock().builder());

		// action
		Location location = service.rentMovie(user, movies);
		
		// verification
		assertThat(location.getReturnDate(), fallsOnMonday());
	}
	
}
