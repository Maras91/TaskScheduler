package scheduler.controller;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import scheduler.model.Task;
import scheduler.repository.TaskRepository;

@Controller
public class TaskModificationController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ViewController viewController;

    @PostMapping("/add")
    public String addTask(String name, String description, Double timeForTask, String date, String startTime, Model model) {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        Long day = dateFormatter.parseDateTime(date).getMillis();

        dateFormatter = DateTimeFormat.forPattern("HH:mm").withZoneUTC();
        Long time = dateFormatter.parseDateTime(startTime).getMillis();
        System.out.println("time in ms: "+ time);
        Task newTask = new Task(name,description,day+time,timeForTask,"");
        taskRepository.save(newTask);

        return viewController.getMainView(model);
    }
    @PostMapping("/update")
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
