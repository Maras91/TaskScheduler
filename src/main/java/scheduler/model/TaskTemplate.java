package scheduler.model;

import javax.persistence.Id;

public class TaskTemplate {
    @Id
    private String name;
    private String description;
    private Double timeForTask;

    public TaskTemplate() {
    }

    public TaskTemplate(String name, String description, Double timeForTask) {
        this.name = name;
        this.description = description;
        this.timeForTask = timeForTask;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getTimeForTask() {
        return timeForTask;
    }
}
