package springreact.practicerestapi.Exceptions;

public class ProjectIdResponse {
    private  String projectIdentifier;

    public ProjectIdResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

}
