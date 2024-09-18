package springreact.practicerestapi.Exceptions;

public class ProjectNotFoundRsponse {
    private  String projectNotFount;

    public ProjectNotFoundRsponse(String projectNotFount) {
        this.projectNotFount = projectNotFount;
    }

    public String getProjectNotFount() {
        return projectNotFount;
    }

    public void setProjectNotFount(String projectNotFount) {
        this.projectNotFount = projectNotFount;
    }

}
