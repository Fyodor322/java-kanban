package tests;

import historyManager.HistoryManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskManager.*;
import tasks.*;
import managers.*;
import enums.*;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    TaskManager taskManager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();
    static Task task;
    static Epic epic;
    static Subtask subtask;

    @BeforeAll
    static void beforeAll(){
        task = new Task("задача1", "опЗадачи1", Progress.NEW);
        epic = new Epic("эпик1", "опЭпик1");
        subtask = new Subtask("Подзадача11", "Подзадача11", epic.getId(), Progress.NEW);
    }

    @Test
    void addNewTask() {
        final int taskId = taskManager.addTask(task);
        final int epicId = taskManager.addTask(epic);
        final int subtaskId = taskManager.addTask(subtask);

        final Task savedTask = taskManager.getTask(taskId);
        final Epic savedEpic = taskManager.getEpic(epicId);
        final Subtask savedSubtask = taskManager.getSubtask(subtaskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");

        assertNotNull(savedSubtask, "Подзадача не найдена.");
        assertEquals(subtask, savedSubtask, "Подзадачи не совпадают.");

        final ArrayList<Task> tasks = taskManager.getTasks();
        final ArrayList<Epic> epics = taskManager.getEpics();
        final ArrayList<Subtask> subtasks = taskManager.getSubtasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");

        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.getFirst(), "Эпики не совпадают.");

        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(subtask, subtasks.getFirst(), "Подзадачи не совпадают.");
    }

    @BeforeEach
    void clearTaskManager(){
        taskManager.removeAllTasks();
        taskManager.removeAllEpics();
        taskManager.removeAllSubtasks();
        historyManager.getHistory().clear();
    }

    @Test
    void tasksWithTheSpecifiedIdDoNotConflict(){
        task.setId(1);
        Epic epic1 = new Epic("epic1", "disEpic1");
        epic1.setId(1);
        Subtask subtask = new Subtask("ST", "DisST", 1, Progress.NEW);
        subtask.setId(1);

        taskManager.addTask(task);

        assertFalse(taskManager.updateTask(epic1), "Нельзя обновить tasks.Task объектом с типом tasks.Epic");
        assertFalse(taskManager.updateTask(subtask), "Нельзя обновить tasks.Task объектом с типом tasks.Subtask");
    }

    @Test
    void checkingForImmutabilityWhenAddingToTheManager(){
        task.setId(11);
        epic.setId(12);
        subtask.setId(13);
        subtask.setEpicId(epic.getId());

        taskManager.addTask(task);
        taskManager.addTask(epic);
        taskManager.addTask(subtask);

        assertEquals(taskManager.getTask(11), task, "id не сохранился");
        assertEquals(taskManager.getEpic(12), epic, "id не сохранился");
        assertEquals(taskManager.getSubtask(13), subtask, "id не сохранился");

        assertEquals(taskManager.getTask(11).getName(), task.getName(), "имя не сохранилось");
        assertEquals(taskManager.getEpic(12).getName(), epic.getName(), "имя не сохранилось");
        assertEquals(taskManager.getSubtask(13).getName(), subtask.getName(), "имя не сохранилось");

        assertEquals(taskManager.getTask(11).getDescription(), task.getDescription(), "описание не сохранилось");
        assertEquals(taskManager.getEpic(12).getDescription(), epic.getDescription(), "описание не сохранилось");
        assertEquals(taskManager.getSubtask(13).getDescription(), subtask.getDescription(), "описание не сохранилось");

        assertEquals(taskManager.getTask(11).getProgress(), task.getProgress(), "прогресс не сохранился");
        assertEquals(taskManager.getEpic(12).getProgress(), epic.getProgress(), "прогресс не сохранился");
        assertEquals(taskManager.getSubtask(13).getProgress(), subtask.getProgress(), "прогресс не сохранился");

        assertTrue(taskManager.getEpic(12).getSubtasks().containsAll(epic.getSubtasks()) && epic.getSubtasks().containsAll(taskManager.getEpic(12).getSubtasks()), "подзадачи эпиков не сохранились");
        assertEquals(taskManager.getSubtask(13).getEpic(), subtask.getEpic(), "эпики подзадач не сохранились");
    }

    @Test
    void testHistory(){
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
        assertEquals(historyManager.getHistory().getFirst(), task, "история не сохраняет задачу");
        task.setName("задача2");
        boolean result = historyManager.getHistory().getFirst().getName().equals(task.getName()) &&
                historyManager.getHistory().getFirst().getDescription().equals(task.getDescription()) &&
                historyManager.getHistory().getFirst().getProgress().equals(task.getProgress());

        assertFalse(result, "история не сохраняет предыдущую версию задачи");
    }
}