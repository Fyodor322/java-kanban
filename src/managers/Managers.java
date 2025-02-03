package managers;

import history.manager.HistoryManager;
import history.manager.InMemoryHistoryManager;
import task.manager.FileBackedTaskManager;
import task.manager.TaskManager;

import java.io.File;


public class Managers {

    public static TaskManager getDefault() {
        return FileBackedTaskManager.loadFromFile(new File("src/resources/base"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
