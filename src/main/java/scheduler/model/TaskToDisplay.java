package scheduler.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TaskToDisplay extends Task {
    private String timeToDisplay;

    public TaskToDisplay(Task task) {
        super(task.getName(), task.getDescription(), task.getDate(),task.getTimeForTask(), task.getWasDone());
        DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm");
        timeToDisplay = new DateTime(this.getDate()).toString(format);
    }

    public String getTimeToDisplay() {
        return timeToDisplay;
    }
}
