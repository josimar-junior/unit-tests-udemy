package br.com.jj.matcher;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.jj.utils.DateUtils;

public class DayOfWeekMatcher extends TypeSafeMatcher<LocalDate> {

	private DayOfWeek dayOfWeek;
	
	public DayOfWeekMatcher(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()));
	}

	@Override
	protected boolean matchesSafely(LocalDate date) {
		return DateUtils.verifyDayOfWeek(date, dayOfWeek);
	}

}
