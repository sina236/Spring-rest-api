package springreact.practicerestapi.web;


import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springreact.practicerestapi.domain.Project;
import springreact.practicerestapi.domain.ProjectTask;
import springreact.practicerestapi.repositories.BacklogRepo;
import springreact.practicerestapi.repositories.ProjectTaskRepo;
import springreact.practicerestapi.services.MapvalidationError;
import springreact.practicerestapi.services.ProjectTaskService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    private ProjectTaskService projectTaskService;
    private MapvalidationError mapvalidationError;

    public BacklogController(ProjectTaskService projectTaskService, MapvalidationError mapvalidationError) {
        this.projectTaskService = projectTaskService;
        this.mapvalidationError = mapvalidationError;
    }


    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result , @PathVariable String backlog_id, Principal principal){
        System.out.println(projectTask);
     ResponseEntity<?> error = mapvalidationError.MapvalidationService(result);
     if(error!=null){
         return  error;
     }
        ProjectTask projectTask1=projectTaskService.addProjectTask(backlog_id,projectTask,principal.getName());
     return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> gettingProjectBackLog(@PathVariable String backlog_id,Principal principal){
        return projectTaskService.findByBackLogById(backlog_id,principal.getName());
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> gettingSequence(@PathVariable String backlog_id , @PathVariable String pt_id,Principal principal){
        ProjectTask projectTask= projectTaskService.findByProjectSequence(backlog_id, pt_id,principal.getName());
        return  new ResponseEntity<>(projectTask,HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updatingTask(@PathVariable String backlog_id,@PathVariable String pt_id, @Valid @RequestBody ProjectTask updatedTask,BindingResult result,
                                          Principal principal ){
        ResponseEntity<?> error= mapvalidationError.MapvalidationService(result);
        if(error!=null){
            return error;
        }

       ProjectTask projectTask= projectTaskService.updatingProjectTask( backlog_id,updatedTask,principal.getName());
        return  new ResponseEntity<>(projectTask,HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deletingProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id,Principal principal){
        projectTaskService.deletingTask(backlog_id,pt_id,principal.getName());

        return new ResponseEntity<>("Project Task has been deleted ",HttpStatus.OK);

    }

}
