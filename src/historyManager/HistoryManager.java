package historyManager;

import tasks.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
    void remove(Task task);
    void removeAllTasks();
    void removeAllSubtasks();
    void removeAllEpics();
}
