import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private Progress progress;
    private int id;

    public Task(String name, String description, Progress progress) {
        this.name = name;
        this.description = description;
        this.progress = progress;
    }

//    public Task(Task task){
//        this.name = task.name;
//        this.description = task.description;
//        this.progress = task.progress;
//        this.id = task.id;
//    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Progress getProgress() {
        return progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && progress == task.progress;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, progress);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description.length='" + description.length() + '\'' +
                ", progress=" + progress +
                ", id=" + id +
                "}\n";
    }
}


