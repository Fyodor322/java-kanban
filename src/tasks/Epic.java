package tasks;

import enums.Progress;
import enums.TaskTypes;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private final ArrayList<Integer> subtasks;

    public Epic(String name, String description) {
        super(name, description, Progress.NEW);
        subtasks = new ArrayList<>();
    }

    public void addSubtask(Integer subtask) {
        if (subtask != this.getId()) {
            subtasks.add(subtask);
        }
    }

    public void removeSubtask(Integer subtask) {
        subtasks.remove(subtask);
    }

    public void clearSubtasks() {
        subtasks.clear();
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,", getId(), TaskTypes.EPIC, getName(), getProgress(), getDescription());
    }

    @Override
    public int hashCode() {
        int prime = 28;
        return prime * Objects.hash(super.hashCode(), subtasks);
    }

    public ArrayList<Integer> getSubtasks() {
        return this.subtasks;
    }
}
