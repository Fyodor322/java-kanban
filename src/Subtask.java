import java.util.ArrayList;

public class Subtask extends Task{
    final private Epic epic;

    public Subtask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
        epic.getSubtasks().add(this);
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + getName() + '\'' +
                ", description.length='" + getDescription().length() + '\'' +
                ", id=" + getId() +
                ", progress=" + getProgress() +
                ", Epic=" + getEpic().getName() +
                "}\n";
    }
}
