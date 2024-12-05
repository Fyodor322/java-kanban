import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void returnCorrectManagers(){
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(taskManager, "getDefault возвращает null");
        assertNotNull(historyManager, "getDefaultHistory возвращает null");

        assertEquals(TaskManager.class, taskManager.getClass(), "getDefault() возвращает не тот тип");
        assertEquals(HistoryManager.class, historyManager.getClass(), "getDefaultHistory() возвращает не тот тип");
    }
}