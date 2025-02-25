package managers;

import historymanager.HistoryManager;
import historymanager.InMemoryHistoryManager;
import taskmanager.InMemoryTaskManager;
import taskmanager.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
