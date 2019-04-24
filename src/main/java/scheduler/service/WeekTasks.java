package scheduler.service;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import scheduler.model.Task;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeekTasks {

    public List<Task> getWeekTasks(List<Task> allTasks) {

        LocalDate actualTime = new LocalDate();
        Long weekStart = actualTime.withDayOfWeek((DateTimeConstants.MONDAY)).toDateTimeAtStartOfDay().getMillis();
        Long weekEnd = actualTime.withDayOfWeek((DateTimeConstants.SUNDAY)).toDateTimeAtStartOfDay()
                .withHourOfDay(23)
                .withMinuteOfHour(59)
                .withSecondOfMinute(59)
                .getMillis();
//        System.out.println("week start: " + weekStart);
//        System.out.println("week end: " + weekEnd);
//        List<Task> weekTasks = allTasks.stream().filter(task -> task.getDate()>weekStart && task.getDate() < weekEnd).collect(Collectors.toList());
//        weekTasks.forEach(task -> System.out.println(new DateTime(task.getDate())));
        return allTasks.stream().filter(task -> task.getDate()>weekStart && task.getDate() < weekEnd).collect(Collectors.toList());
    }
    public List<Task> getDailyTasks(int dayOfWeek,List<Task> tasks) {
        LocalDate actualTime = new LocalDate();
        Long dayStart = actualTime.withDayOfWeek(dayOfWeek).toDateTimeAtStartOfDay().getMillis();
        Long dayEnd = actualTime.withDayOfWeek(dayOfWeek).toDateTimeAtStartOfDay()
                .withHourOfDay(23)
                .withMinuteOfHour(59)
                .withSecondOfMinute(59)
                .getMillis();
        return tasks.stream().filter(task -> task.getDate()>=dayStart && task.getDate() <= dayEnd).collect(Collectors.toList());
    }
}
