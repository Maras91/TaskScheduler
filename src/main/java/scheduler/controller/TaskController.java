package scheduler.controller;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import scheduler.model.Task;
import scheduler.model.TaskToDisplay;
import scheduler.repository.TaskRepository;
import scheduler.service.WeekTasks;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private WeekTasks weekTasks;

    @GetMapping("/")
    public String getAllNotes(Model model) {
        List<Task> weeklyTasks = weekTasks.getWeekTasks(taskRepository.findAll());

        List<TaskToDisplay> dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.MONDAY,weeklyTasks)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("mondayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.TUESDAY,weeklyTasks)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("tuesdayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.WEDNESDAY,weeklyTasks)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("wednesdayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.THURSDAY,weeklyTasks)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("thursdayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.FRIDAY,weeklyTasks)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("fridayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.SATURDAY,weeklyTasks)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("saturdayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.SUNDAY,weeklyTasks)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("sundayTasks",dailyTasks);
        return "index";
    }
    //todo wyswietla sie zla godzina
    @PostMapping("/add")
    public String addTask(String name, String description, Double timeForTask, String date, String startTime, Model model) {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        Long day = dateFormatter.parseDateTime(date).getMillis();

        dateFormatter = DateTimeFormat.forPattern("HH:mm").withZoneUTC();
        Long time = dateFormatter.parseDateTime(startTime).getMillis();
        System.out.println("time in ms: "+ time);
        Task newTask = new Task(name,description,day+time,timeForTask,"N");
        taskRepository.save(newTask);

        return getAllNotes(model);
    }
}
