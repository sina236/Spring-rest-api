package springreact.practicerestapi.services;


import org.springframework.stereotype.Service;
import springreact.practicerestapi.Exceptions.ProjectIdException;
import springreact.practicerestapi.Exceptions.ProjectNotFoundException;
import springreact.practicerestapi.domain.Backlog;
import springreact.practicerestapi.domain.Project;
import springreact.practicerestapi.domain.UserAccount;
import springreact.practicerestapi.repositories.BacklogRepo;
import springreact.practicerestapi.repositories.Projectrepo;
import springreact.practicerestapi.repositories.UserRepo;

@Service
public class ProjectService {
    private final Projectrepo projectrepo;
    private final BacklogRepo backlogRepo;
    private final UserRepo userRepo;

    public ProjectService(Projectrepo projectrepo, BacklogRepo backlogRepo, UserRepo userRepo) {
        this.projectrepo = projectrepo;
        this.backlogRepo = backlogRepo;
        this.userRepo = userRepo;
    }

    public Project saveOurProject(Project project,String username){
        try {
            if(project.getId()!=null){
                Project project1=projectrepo.findByProjectIdentifier(project.getProjectIdentifier());
                if(project1!=null&&(!project1.getProjectLeader().equals(username))){
                    throw new ProjectNotFoundException("Project not found in your account");
                }else if(project1==null){
                    throw new ProjectNotFoundException("Project is not available");
                }
            }
            UserAccount userAccount=userRepo.findByUsername(username);
            project.setUserAccount(userAccount);
            project.setProjectLeader(userAccount.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if(project.getId()==null){
                Backlog backlog= new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if(project.getId()!=null){
                project.setBacklog(backlogRepo.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectrepo.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID "+project.getProjectIdentifier().toUpperCase()+"already taken");
        }
    }

    public Project findProjectByIdentifier(String projectId,String username){
        Project project= projectrepo.findByProjectIdentifier(projectId.toUpperCase());
        if(project==null){
            throw  new ProjectIdException("Project ID "+projectId+"does not exist");
        }
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        System.out.println(username);
        System.out.println(68);
        return  projectrepo.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId,String username){
        projectrepo.delete(findProjectByIdentifier(projectId,username));
    }





}
