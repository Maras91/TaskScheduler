package scheduler.controller;

import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import scheduler.model.Task;
import scheduler.model.TaskTemplate;
import scheduler.model.TaskToDisplay;
import scheduler.repository.TaskRepository;
import scheduler.repository.TaskTemplateRepository;
import scheduler.service.WeekTasks;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;

    @Autowired
    private WeekTasks weekTasks;

    private int weekNumber;

    public ViewController() {
        this.weekNumber = 0;
    }

    @GetMapping("/")
    public String getMainView(Model model) {

        model.addAttribute("taskTemplates",taskTemplateRepository.findAll());
        List<Task> weeklyTasks = weekTasks.getWeekTasks(taskRepository.findAll(Sort.by("date")),weekNumber);

        List<TaskToDisplay> dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.MONDAY,weeklyTasks,weekNumber)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("mondayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.TUESDAY,weeklyTasks,weekNumber)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("tuesdayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.WEDNESDAY,weeklyTasks,weekNumber)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("wednesdayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.THURSDAY,weeklyTasks,weekNumber)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("thursdayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.FRIDAY,weeklyTasks,weekNumber)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("fridayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.SATURDAY,weeklyTasks,weekNumber)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("saturdayTasks",dailyTasks);

        dailyTasks = weekTasks.getDailyTasks(DateTimeConstants.SUNDAY,weeklyTasks,weekNumber)
                .stream().map(TaskToDisplay::new).collect(Collectors.toList());
        model.addAttribute("sundayTasks",dailyTasks);
        return "index";
    }

    @PostMapping("/weekBefore")
    public String setWeekBefore(Model model){
        weekNumber--;
        return getMainView(model);
    }

    @PostMapping("/weekAfter")
    public String setWeekAfter(Model model){
        weekNumber++;
        return getMainView(model);
    }

    @PostMapping("/thisWeek")
    public String setThisWeek(Model model){
        weekNumber=0;
        return getMainView(model);
    }

    @GetMapping("/taskmodificationview")
    public String detTaskModificationWindow() {
        return "taskmodificationwindow";
    }

    @PostMapping("/addView")
    public String getAddView(String taskTemplateName, Model model){
        TaskTemplate taskTemplate;
        if(taskTemplateName.equals("none")){
            taskTemplate = new TaskTemplate("","", (double) 0);
        } else {
            taskTemplate = taskTemplateRepository.findById(taskTemplateName).get();
        }
        model.addAttribute("taskTemplate",taskTemplate);
        return "addtaskwindow";
    }

    @PostMapping("/addTemplateView")
    public String getAddTemlateView () {
        return "addtasktemplatewindow";
    }


    public String getTemplateModificationView(Model model) {
        return "templatemodificationwindow";
    }
}
