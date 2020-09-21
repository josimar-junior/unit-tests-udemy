package br.com.jj.matcher;

import static br.com.jj.utils.DateUtils.getDateWithDifferenceOfDays;
import static br.com.jj.utils.DateUtils.isSameDate;

import java.time.LocalDate;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DateDifferenceDays extends TypeSafeMatcher<LocalDate> {

	private Integer qtdDays;
	
	public DateDifferenceDays(Integer qtdDays) {
		this.qtdDays = qtdDays;
	}

	@Override
	public void describeTo(Description description) {

	}

	@Override
	protected boolean matchesSafely(LocalDate date) {
		return isSameDate(date, getDateWithDifferenceOfDays(qtdDays));
	}

}
