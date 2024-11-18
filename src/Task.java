import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private final int id;
    private Progress progress;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.progress = Progress.NEW;
        this.id = this.hashCode();
    }

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

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && progress == task.progress;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        return prime * Objects.hash(name, description, id, progress);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description.length='" + getDescription().length() + '\'' +
                ", id=" + id +
                ", progress=" + progress +
                "}\n";
    }
}


