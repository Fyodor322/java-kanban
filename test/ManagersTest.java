

import history.manager.HistoryManager;
import history.manager.InMemoryHistoryManager;
import org.junit.jupiter.api.Test;
import task.manager.InMemoryTaskManager;
import task.manager.TaskManager;
import managers.*;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void returnCorrectManagers() {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(taskManager, "getDefault возвращает null");
        assertNotNull(historyManager, "getDefaultHistory возвращает null");

        assertEquals(task.manager.FileBackedTaskManager.class, taskManager.getClass(), "getDefault() возвращает не тот тип");
        assertEquals(InMemoryHistoryManager.class, historyManager.getClass(), "getDefaultHistory() возвращает не тот тип");
    }
}