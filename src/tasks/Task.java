package tasks;

import enums.Progress;
import enums.TaskTypes;

import java.util.Objects;

public class Task implements TaskIn {
    private String name;
    private String description;
    private Progress progress;
    private int id;

    public Task(String name, String description, Progress progress) {
        this.name = name;
        this.description = description;
        this.progress = progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (task.id == this.id) return true;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && progress == task.progress;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, progress);
    }

    @Override
    public String toString() {
        return id + "," + TaskTypes.TASK + "," + name + "," + progress + "," + description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Progress getProgress() {
        return this.progress;
    }

    public int getId() {
        return this.id;
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

    public void setId(int id) {
        this.id = id;
    }
}


