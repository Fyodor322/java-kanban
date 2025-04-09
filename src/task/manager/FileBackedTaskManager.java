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
    private static final String databaseHeader = "id,type,name,status,description,epic";

    public FileBackedTaskManager(Path path) {
        this.path = path;
    }

    public void save() {
        try {
            try (Writer fileCleaner = new FileWriter(path.toFile())) {
                fileCleaner.write("");
            }
            try (Writer fileWriter = new FileWriter(path.toFile(), true)) {
                fileWriter.write(databaseHeader);
                fileWriter.write(System.lineSeparator());
                for (TaskIn task : getEpics()) {
                    fileWriter.write(task.toString());
                    fileWriter.write(System.lineSeparator());
                }
                for (TaskIn task : getTasks()) {
                    fileWriter.write(task.toString());
                    fileWriter.write(System.lineSeparator());
                }
                for (TaskIn task : getSubtasks()) {
                    fileWriter.write(task.toString());
                    fileWriter.write(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("ошибка при работе с файлом");
        }

    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.toPath());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            while (br.ready()) {
                String str = br.readLine();
                if (!str.isEmpty()) {
                    fileBackedTaskManager.addTask(fromString(str));
                }
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

        switch (taskTypes) {
            case TaskTypes.SUBTASK:
                int epic = Integer.parseInt(arguments[5].trim());
                task = new Subtask(name, description, epic, progress);
                task.setId(id);
                break;
            case TaskTypes.EPIC:
                task = new Epic(name, description);
                task.setId(id);
                break;
            default:
                task = new Task(name, description, progress);
                task.setId(id);
                break;
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
    public int addTask(Subtask subtask) {
        int id = super.addTask(subtask);
        save();
        return id;
    }

    @Override
    public int addTask(Epic epic) {
        int id = super.addTask(epic);
        save();
        return id;
    }

    @Override
    public int addTask(Task task) {
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
