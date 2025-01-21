
import enums.Progress;
import historymanager.*;
import managers.Managers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import taskmanager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    static TaskManager taskManager = Managers.getDefault();
    static HistoryManager historyManager = Managers.getDefaultHistory();
    static Task task;
    static Epic epic;
    static Subtask subtask;

    @BeforeAll
    static void beforeAll() {
        task = new Task("задача1", "опЗадачи1", Progress.NEW);
        epic = new Epic("эпик1", "опЭпик1");
        taskManager.addTask(task);
        taskManager.addTask(epic);

        subtask = new Subtask("Подзадача11", "Подзадача11", epic.getId(), Progress.NEW);
        taskManager.addTask(subtask);
    }

    @Test
    void add() {
        System.out.println("задача: " + taskManager.getTask(task.getId()));
        System.out.println("эпик: " + taskManager.getEpic(epic.getId()));
        System.out.println("подзадача: " + taskManager.getSubtask(subtask.getId()));
        List<Task> testTasks = new LinkedList<>();
        testTasks.add(task);
        testTasks.add(epic);
        testTasks.add(subtask);

        assertEquals(taskManager.getHistory().size(), 3, "история пустая");
        for (int i = 0; i < 3; i++) {
            assertEquals(taskManager.getHistory().get(i), testTasks.get(i), "порядок не сохранён");
        }

        System.out.println("задача: " + taskManager.getTask(task.getId()));
        testTasks.remove(task);
        testTasks.add(task);
        for (int i = 0; i < 3; i++) {
            assertEquals(taskManager.getHistory().get(i), testTasks.get(i), "некорректное обновление списка при повторном обращении к задаче");
        }
    }

    @Test
    void remove() {
        historyManager.remove(2);
        for (Task elTask : historyManager.getHistory()) {
            assertEquals(task, elTask, "подзадача эпика не удаляется при удалении эпика");
        }
        taskManager.addTask(epic);
        taskManager.addTask(subtask);
        Set<Integer> listNum = new HashSet<>(3);
        listNum.add(1);
        listNum.add(3);

        historyManager.remove(listNum);
        for (Task elTask : historyManager.getHistory()) {
            assertEquals(epic, elTask, "удаление сразу нескольких элементов не работает");
        }
    }

}