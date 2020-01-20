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
import scheduler.service.WeekTasks;

import java.util.*;

@Controller
public class TaskModificationController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ViewController viewController;

    @Autowired
    private WeekTasks weekTasks;

    @PostMapping("/add")
    public String addTask(String name, String description, Double timeForTask, String date, String startTime , boolean addForAllDays, Model model) {
        if (date.isEmpty()){
            date = LocalDate.now().toString();
        }
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
            return weekTasks.createTaskToAllWeek(newTask);
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
