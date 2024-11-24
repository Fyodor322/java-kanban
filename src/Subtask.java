import java.util.Objects;

public class Subtask extends Task{
    private final int epicId;

    public Subtask(String name, String description, int epicId, Progress progress) {
        super(name, description, progress);
        this.epicId = epicId;
    }

    public int getEpic() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + getName() + '\'' +
                ", description.length='" + getDescription().length() + '\'' +
                ", progress=" + getProgress() +
                ", id=" + getId() +
                ", EpicId=" + epicId +
                "}\n";
    }

    @Override
    public int hashCode() {
        int prime = 31;
        return prime * Objects.hash(super.hashCode(), epicId);
    }
}
