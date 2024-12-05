import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    ArrayList<Subtask> getSubtasks();

    ArrayList<Epic> getEpics();

    ArrayList<Task> getTasks();

    void removeAllTasks();

    void removeAllSubtasks();

    void removeAllEpics();

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    int addTask(Subtask subtask);

    int addTask(Epic epic);

    int addTask(Task task);

    <T extends Task> boolean updateTask(T task);

    void removeTask(int id);

    void removeSubtask(int id);

    void removeEpic(int id);

    List<Task> getHistory();

    ArrayList<Subtask> getSubtasks(int id);
}
