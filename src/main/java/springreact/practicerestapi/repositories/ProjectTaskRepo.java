package springreact.practicerestapi.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springreact.practicerestapi.domain.ProjectTask;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface ProjectTaskRepo extends CrudRepository<ProjectTask,Long> {
    List<ProjectTask> findByProjectIdentiferOrderByPriority(String id);
    ProjectTask findByProjectSequence(String sequence);

}
