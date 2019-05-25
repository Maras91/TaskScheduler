package scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import scheduler.model.TaskTemplate;
import scheduler.repository.TaskTemplateRepository;

@Controller
public class TaskTemplateController {

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;

    @Autowired
    private ViewController viewController;

    @PostMapping ("/addTemplate")
    public String addTaskTemplate (String name,String description, Double timeForTask, Model model){
        taskTemplateRepository.save(new TaskTemplate(name, description,timeForTask));
        return viewController.getMainView(model);
    }

    @PostMapping("/modifyTemplate")
    public String modifyTaskTemplate(String templateName,String action, Model model) {
        if (action.equals("delete")) {
            taskTemplateRepository.deleteById(templateName);
            return viewController.getMainView(model);
        }
        else {
            //todo model add templateName, add update mechanism
            return viewController.getMainView(model);
        }

    }
}
