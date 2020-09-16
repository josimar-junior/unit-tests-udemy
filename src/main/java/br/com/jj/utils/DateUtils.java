package br.com.jj.utils;

import java.time.LocalDate;

public class DateUtils {

	public static LocalDate addDays(LocalDate date, int days) {
		return date.plusDays(days);
	}
	
	public static LocalDate subtractDays(LocalDate date, int days) {
		return date.minusDays(days);
	}
	
	public static LocalDate getDateWithDifferenceOfDays(int days) {
		return days > 0 ? addDays(LocalDate.now(), days) : subtractDays(LocalDate.now(), days);
	}
	
	public static LocalDate getDate(int day, int month, int year) {
		return LocalDate.of(year, month, day);
	}
	
	public static boolean isSameDate(LocalDate date1, LocalDate date2) {
		return date1.isEqual(date2);
	}
	
	public static boolean verifyDayOfWeek(LocalDate date, int day) {
		return date.getDayOfWeek().getValue() == day;
	}
}
