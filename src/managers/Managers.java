package managers;

import history_manager.HistoryManager;
import history_manager.InMemoryHistoryManager;
import task_manager.InMemoryTaskManager;
import task_manager.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
