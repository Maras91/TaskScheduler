package scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import scheduler.model.Task;
import scheduler.repository.TaskRepository;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/all")
    public List<Task> getAllNotes() {
        return taskRepository.findAll();
    }
}
