import enums.Progress;
import exceptions.ManagerSaveException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.manager.FileBackedTaskManager;

import tasks.Epic;
import tasks.Task;
import tasks.TaskIn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    FileBackedTaskManager taskManager = FileBackedTaskManager.loadFromFile(new File("src/resources/base"));

    @BeforeEach
    void cleanFile() {
        taskManager.removeAllTasks();
        taskManager.removeAllSubtasks();
        taskManager.removeAllEpics();
    }

    @Test
    void savingMultiplyTasks() {

        List<TaskIn> tasks = new ArrayList<>();
        List<TaskIn> epics = new ArrayList<>();

        Task task = new Task("Task1", "disTask1", Progress.NEW);
        Epic epic = new Epic("epic1", "disEpic1");

        tasks.add(task);
        epics.add(epic);

        taskManager.addTask(task);
        taskManager.addTask(epic);

        try (BufferedReader br = new BufferedReader(new FileReader(taskManager.getPath().toFile()))) {
            while (br.ready()) {
                String strObject = br.readLine();
                assertTrue(tasks.contains(FileBackedTaskManager.fromString(strObject)) ||
                        epics.contains(FileBackedTaskManager.fromString(strObject)));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("ошибка при работе с файлом");
        }
    }

    @Test
    void loadFromFile() {
        Task task1 = new Task("Task1", "disTask1", Progress.NEW);
        Task task2 = new Task("Task2", "disTask2", Progress.NEW);
        Task task3 = new Task("Task3", "disTask3", Progress.IN_PROGRESS);
        Task task4 = new Task("Task4", "disTask4", Progress.NEW);

        List<TaskIn> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);

        try (Writer fileCleaner = new FileWriter(taskManager.getPath().toFile())) {
            for (TaskIn task : tasks) {
                fileCleaner.write(task.toString());
            }
        } catch (IOException e) {
            throw new ManagerSaveException("ошибка при работе с файлом");
        }

        FileBackedTaskManager taskManager1 = FileBackedTaskManager.loadFromFile(new File("src/resources/base"));
        try (BufferedReader br = new BufferedReader(new FileReader(taskManager1.getPath().toFile()))) {
            while (br.ready()) {
                String strObject = br.readLine();
                assertTrue(tasks.contains(FileBackedTaskManager.fromString(strObject)));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("ошибка при работе с файлом");
        }

    }
}