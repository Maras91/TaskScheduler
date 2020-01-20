package scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import scheduler.model.Task;
import scheduler.repository.TaskRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StatisticController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/hardestTask")
    private String getTheMostDifficultTask(Model model) {
        List<Task> allFailTasks = taskRepository.findAll()
                .stream()
                .filter(t ->t.getWasDone().equals("N"))
                .collect(Collectors.toList());
        Map<String, Integer> numberOfFailTasks = new HashMap<>();
        Integer highestNumberOfFailures=0;
        String hardestTask = "None";
        for (Task task :allFailTasks) {
            Integer number;
            if (numberOfFailTasks.get(task.getName())==null) {
                numberOfFailTasks.put(task.getName(),1);
                number = 1;
            } else {
                number = numberOfFailTasks.get(task.getName());
                ++number;
                numberOfFailTasks.replace(task.getName(),number);
            }
            if (number>=highestNumberOfFailures) {
                if (number.equals(highestNumberOfFailures)) {
                    hardestTask += ", "+task.getName();
                } else {
                    highestNumberOfFailures = number;
                    hardestTask = task.getName();
                }
            }
        }
        model.addAttribute("score", hardestTask);
        return "statisticscorewindow";
    }
}
