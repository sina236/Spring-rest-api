package springreact.practicerestapi.services;

import org.springframework.stereotype.Service;
import springreact.practicerestapi.Exceptions.ProjectNotFoundException;
import springreact.practicerestapi.domain.Backlog;
import springreact.practicerestapi.domain.Project;
import springreact.practicerestapi.domain.ProjectTask;
import springreact.practicerestapi.repositories.BacklogRepo;
import springreact.practicerestapi.repositories.ProjectTaskRepo;
import springreact.practicerestapi.repositories.Projectrepo;

@Service
public class ProjectTaskService {
    private final BacklogRepo backlogRepo;
    private final ProjectTaskRepo projectTaskRepo;
    private final Projectrepo projectrepo;
    private final ProjectService projectService;


    public ProjectTaskService(BacklogRepo backlogRepo, ProjectTaskRepo projectTaskRepo, Projectrepo projectrepo, ProjectService projectService) {
        this.backlogRepo = backlogRepo;
        this.projectTaskRepo = projectTaskRepo;
        this.projectrepo = projectrepo;
        this.projectService = projectService;
    }


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){
            Backlog backlog =projectService.findProjectByIdentifier(projectIdentifier,username).getBacklog();
            projectTask.setBacklog(backlog);
            Integer backSeq= backlog.getPTSequence();
            backSeq++;
            backlog.setPTSequence(backSeq);
            projectTask.setProjectSequence(projectIdentifier+"-"+backSeq);
            projectTask.setProjectIdentifer(projectIdentifier);
            if( projectTask.getPriority()==null){
                projectTask.setPriority(3);
            }
            if(projectTask.getStatus()==null|| projectTask.getStatus()==""){
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepo.save(projectTask);

    }

    public Iterable<ProjectTask> findByBackLogById(String backlog_id,String username) {
        projectService.findProjectByIdentifier(backlog_id,username);
        return projectTaskRepo.findByProjectIdentiferOrderByPriority(backlog_id);
    }

    public ProjectTask findByProjectSequence(String backlog_id ,String seq,String username){
        //Make sure we are searching on the right backlog
        projectService.findProjectByIdentifier(backlog_id,username);
        ProjectTask projectTask= projectTaskRepo.findByProjectSequence(seq);
        if(projectTask==null){
            throw new ProjectNotFoundException("This task does not exist");
        }
        if(!projectTask.getProjectIdentifer().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task does not exist in this project ");
        }

        return projectTaskRepo.findByProjectSequence(seq);
    }

    public ProjectTask updatingProjectTask(String backlog_id,ProjectTask updatedProject,String username){
        System.out.println(updatedProject.getProjectSequence());
        ProjectTask projectTask= findByProjectSequence(backlog_id,updatedProject.getProjectSequence(),username);
        projectTask=updatedProject;
        return projectTaskRepo.save(projectTask);
    }

    public void deletingTask(String backlog_id, String pt_id,String username){
        ProjectTask projectTask= findByProjectSequence(backlog_id,pt_id,username);
        if(projectTask==null){
            throw new ProjectNotFoundException("This task does not exist");
        }

        projectTaskRepo.delete(projectTask);
    }
}
