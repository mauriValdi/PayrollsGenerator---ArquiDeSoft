package payrollcasestudy.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class TimeCard {
    private Calendar date;
    private double hours;

    public TimeCard(Calendar date, double hours) {
        this.date = date;
        this.hours = hours;
    }

    public Calendar getDate() {
        return date;
    }

    public double getHours() {
        return hours;
    }
    
    public Date getDateFormat(){
    	return date.getTime();
    }
    
    public LocalDate getAsLocalDate()
    {
    	LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDate localDate = dateTime.toLocalDate();
		return localDate;
    }
}
