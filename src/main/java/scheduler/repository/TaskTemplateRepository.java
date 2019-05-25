package scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scheduler.model.TaskTemplate;

@Repository
public interface TaskTemplateRepository extends JpaRepository<TaskTemplate,String> {
}
