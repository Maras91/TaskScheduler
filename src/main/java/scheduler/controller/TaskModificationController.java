package scheduler.controller;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import scheduler.model.Task;
import scheduler.repository.TaskRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TaskModificationController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ViewController viewController;

    @PostMapping("/add")
    public String addTask(String name, String description, Double timeForTask, String date, String startTime , boolean addForAllDays, Model model) {

        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        Long day = dateFormatter.parseDateTime(date).getMillis();
        dateFormatter = DateTimeFormat.forPattern("HH:mm").withZoneUTC();
        Long time = dateFormatter.parseDateTime(startTime).getMillis();
        Task newTask = new Task(name, description, day + time, timeForTask, "");
        List<Task>newTasks =addTasksToList(newTask,addForAllDays);
        newTasks.forEach(task -> taskRepository.save(task));

        return viewController.getMainView(model);
    }

    private List<Task> addTasksToList(Task newTask, boolean addForAllDays) {
        if (addForAllDays) {
            long date = newTask.getDate();
            LocalDate localDate = new LocalDate(date);

            long weekStart = localDate.withDayOfWeek((DateTimeConstants.MONDAY)).toDateTimeAtStartOfDay().getMillis();
            long weekEnd = localDate.withDayOfWeek((DateTimeConstants.SUNDAY)).toDateTimeAtStartOfDay()
                    .withHourOfDay(23)
                    .withMinuteOfHour(59)
                    .withSecondOfMinute(59)
                    .getMillis();
            List<Task> allTasks = IntStream.range(-6,6)
                    .mapToObj(multiplier -> newTask.duplicateTask(date+DateTimeConstants.MILLIS_PER_DAY*multiplier))
                    .filter(task -> task.getDate()<weekEnd && task.getDate()>weekStart)
                    .collect(Collectors.toList());
            return allTasks;
        } else {
            return Collections.singletonList(newTask);
        }
    }

    @PostMapping("/updateTask")
    public String updateTask(Long date, String wasDone, Model model) {
        Task taskToUpdate = taskRepository.getOne(date);
        taskToUpdate.setWasDone(wasDone);
        taskRepository.save(taskToUpdate);
        return viewController.getMainView(model);
    }
    @PostMapping("/delete")
    public String deleteTask(Long date, Model model) {
        taskRepository.deleteById(date);
        return viewController.getMainView(model);
    }

}
