package scheduler.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name ="Task")
public class Task {

    @Id
    private String name;
    private String description;
    private Date date;
    private String wasDone;

    public Task() {
    }

    public Task(String name, String description, Date date, String wasDone) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.wasDone = wasDone;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getWasDone() {
        return wasDone;
    }
}
