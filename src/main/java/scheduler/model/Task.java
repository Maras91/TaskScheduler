package scheduler.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="task")
public class Task {

    @Id
    private Long date;
    private String name;
    private String description;
    private Double timeForTask;
    private String wasDone;


    public Task() {
    }

    public Task(String name, String description, Long date, Double timeForTask, String wasDone) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.timeForTask = timeForTask;
        this.wasDone = wasDone;
    }
    public Task duplicateTask (Long date) {
        return new Task (this.name, this.description, date, this.timeForTask, this.wasDone);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getDate() {
        return date;
    }

    public Double getTimeForTask() {
        return timeForTask;
    }

    public String getWasDone() {
        return wasDone;
    }

    public void setWasDone(String wasDone) {
        this.wasDone = wasDone;
    }
}
