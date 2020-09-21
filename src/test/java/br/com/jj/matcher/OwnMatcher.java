package br.com.jj.matcher;

import java.time.DayOfWeek;

public class OwnMatcher {

	public static DayOfWeekMatcher fallsOn(DayOfWeek dayOfWeek) {
		return new DayOfWeekMatcher(dayOfWeek);
	}
	
	public static DayOfWeekMatcher fallsOnMonday() {
		return fallsOn(DayOfWeek.MONDAY);
	}
}
