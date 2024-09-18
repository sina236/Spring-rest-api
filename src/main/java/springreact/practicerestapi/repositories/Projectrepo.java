package springreact.practicerestapi.repositories;


import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springreact.practicerestapi.domain.Project;

@Repository
public interface Projectrepo extends CrudRepository<Project, Long> {
    Project findByProjectIdentifier(String projectId);

    @Override
    Iterable<Project> findAll();

    Iterable<Project> findAllByProjectLeader(String username);

}
