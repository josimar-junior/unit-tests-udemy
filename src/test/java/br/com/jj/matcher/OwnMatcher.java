package br.com.jj.matcher;

import java.time.DayOfWeek;

public class OwnMatcher {

	public static DayOfWeekMatcher fallsOn(DayOfWeek dayOfWeek) {
		return new DayOfWeekMatcher(dayOfWeek);
	}
	
	public static DayOfWeekMatcher fallsOnMonday() {
		return fallsOn(DayOfWeek.MONDAY);
	}
	
	public static DateDifferenceDays isToday() {
		return isTodayWithDifferenceDays(0);
	}
	
	public static DateDifferenceDays isTodayWithDifferenceDays(Integer qtdDays) {
		return new DateDifferenceDays(qtdDays);
	}
}
