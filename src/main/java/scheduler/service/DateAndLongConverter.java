package scheduler.service;

import org.joda.time.DateTime;

public class DateAndLongConverter {
    public DateTime convetLongToDate (Long dateInMilliseconds) {
        return new DateTime(dateInMilliseconds);
    }
    public Long convertDateToLong (DateTime dateTime) {
        return dateTime.getMillis();
    }
}
