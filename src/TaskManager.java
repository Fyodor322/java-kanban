import java.util.ArrayList;
import java.util.Collection;

public interface TaskManager {
    Collection<Subtask> getSubtasks();

    Collection<Epic> getEpics();

    Collection<Task> getTasks();

    void removeAllTasks();

    void removeAllSubtasks();

    void removeAllEpics();

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    void addTask(Subtask subtask);

    void addTask(Epic epic);

    void addTask(Task task);

    void updateTask(Task task);

    void removeTask(int id);

    void removeSubtask(int id);

    void removeEpic(int id);

    Collection<Task> getHistory();

    ArrayList<Subtask> getSubtasks(int id);
}
