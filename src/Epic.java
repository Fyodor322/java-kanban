import java.util.ArrayList;

public class Epic extends Task{
    private final ArrayList<Subtask> subtasks;

    public Epic(String name, String description, Progress progress) {
        super(name, description, progress);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask){
        subtasks.add(subtask);
    }

    public void removeSubtask(int id){
        subtasks.remove(id);
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
}
