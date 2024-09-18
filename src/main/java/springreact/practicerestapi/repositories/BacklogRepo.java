package springreact.practicerestapi.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springreact.practicerestapi.domain.Backlog;


@Repository
public interface BacklogRepo extends CrudRepository<Backlog,Long> {
    Backlog findByProjectIdentifier(String identifier);
}
