package scheduler.service;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import scheduler.model.Task;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeekTasks {

    public LocalDate setWeekShift(int weekNumber) {
        if (0<weekNumber) {
            weekNumber=weekNumber*(-1);
            return new LocalDate().minusWeeks(weekNumber);
        } else {
            return new LocalDate().plusWeeks(weekNumber);
        }
    }

    public List<Task> getWeekTasks(List<Task> allTasks, int weekNumber) {

        LocalDate weekShift = setWeekShift(weekNumber);

        Long weekStart = weekShift.withDayOfWeek((DateTimeConstants.MONDAY)).toDateTimeAtStartOfDay().getMillis();
        Long weekEnd = weekShift.withDayOfWeek((DateTimeConstants.SUNDAY)).toDateTimeAtStartOfDay()
                .withHourOfDay(23)
                .withMinuteOfHour(59)
                .withSecondOfMinute(59)
                .getMillis();
        return allTasks.stream().filter(task -> task.getDate()>weekStart && task.getDate() < weekEnd).collect(Collectors.toList());
    }

    public List<Task> getDailyTasks(int dayOfWeek,List<Task> tasks, int weekNumber) {
        LocalDate weekShift = setWeekShift(weekNumber);

        Long dayStart = weekShift.withDayOfWeek(dayOfWeek).toDateTimeAtStartOfDay().getMillis();
        Long dayEnd = weekShift.withDayOfWeek(dayOfWeek).toDateTimeAtStartOfDay()
                .withHourOfDay(23)
                .withMinuteOfHour(59)
                .withSecondOfMinute(59)
                .getMillis();
        return tasks.stream().filter(task -> task.getDate()>=dayStart && task.getDate() <= dayEnd).collect(Collectors.toList());
    }
}
