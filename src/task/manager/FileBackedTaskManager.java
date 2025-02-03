package task.manager;

import enums.Progress;
import enums.TaskTypes;
import exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskIn;

import java.io.*;
import java.nio.file.Path;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final Path path;

    public FileBackedTaskManager(Path path) {
        this.path = path;
    }

    public void save() {
        try {
            try (Writer fileCleaner = new FileWriter(path.toFile())) {
                fileCleaner.write("");
            }
            try (Writer fileWriter = new FileWriter(path.toFile(), true)) {
                for (TaskIn task : getEpics()) {
                    fileWriter.write(task.toString() + "\n");
                }
                for (TaskIn task : getTasks()) {
                    fileWriter.write(task.toString() + "\n");
                }
                for (TaskIn task : getSubtasks()) {
                    fileWriter.write(task.toString() + "\n");
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("ошибка при работе с файлом");
        }

    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.toPath());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                fileBackedTaskManager.addTask(fromString(br.readLine()));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("ошибка при работе с файлом");
        }
        return fileBackedTaskManager;
    }

    public static Task fromString(String value) {
        String[] arguments = value.split(",");
        Task task;

        int id = Integer.parseInt(arguments[0]);
        TaskTypes taskTypes = TaskTypes.valueOf(arguments[1]);
        String name = arguments[2];
        Progress progress = Progress.valueOf(arguments[3]);
        String description = arguments[4];

        if (taskTypes.equals(TaskTypes.TASK)) {
            task = new Task(name, description, progress);
            task.setId(id);
        } else if (taskTypes.equals(TaskTypes.EPIC)) {
            task = new Epic(name, description);
            task.setId(id);
        } else {
            int epic = Integer.parseInt(arguments[5]);
            task = new Subtask(name, description, epic, progress);
            task.setId(id);
        }

        return task;
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public <T extends Task> int addTask(T task) {
        int id = super.addTask(task);
        save();
        return id;
    }

    @Override
    public <T extends Task> boolean updateTask(T task) {
        boolean success = super.updateTask(task);
        save();
        return success;
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeTask(id);
        save();
    }

    public Path getPath() {
        return path;
    }
}
