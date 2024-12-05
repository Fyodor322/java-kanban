import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task{
    private final ArrayList<Integer> subtasks;


    public Epic(String name, String description) {
        super(name, description, Progress.NEW);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Integer subtask){
        if (subtask != this.getId()){
            subtasks.add(subtask);
        }
    }

    public void removeSubtask(Integer subtask){
        subtasks.remove(subtask);
    }

    public void clearSubtasks(){
        subtasks.clear();
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + getName() + '\'' +
                ", description.length='" + getDescription().length() + '\'' +
                ", id=" + getId() +
                ", progress=" + getProgress() +
                ", subtasks=" + getSubtasks().size() +
                "}\n";
    }

    @Override
    public int hashCode() {
        int prime = 28;
        return prime * Objects.hash(super.hashCode(), subtasks);
    }
}
