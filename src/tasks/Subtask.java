package tasks;

import enums.*;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int epicId, Progress progress) {
        super(name, description, progress);
        if (this.getId() != epicId) {
            this.epicId = epicId;
        } else {
            this.epicId = 0;
        }
    }

    public int getEpic() {
        return epicId;
    }

    public boolean setEpicId(int epicId) {
        if (epicId == this.getId()) {
            return false;
        }
        this.epicId = epicId;
        return true;
    }

    @Override
    public String toString() {
        return getId() + "," + TaskTypes.SUBTASK + "," + getName() + "," + getProgress() + "," + getDescription() + "," + getEpic();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        return prime * Objects.hash(super.hashCode(), epicId);
    }
}
