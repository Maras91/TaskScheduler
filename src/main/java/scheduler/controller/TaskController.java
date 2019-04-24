package scheduler.controller;

import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/all")
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
}
