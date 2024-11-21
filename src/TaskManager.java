import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;

    public TaskManager() {
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void removeAllTasks() {
        subtasks.clear();
        epics.clear();
        System.out.println("Все задачи удалены");
    }

    public Task getTask(int id) {
        Epic epic = epics.get(id);
        Subtask subtask = subtasks.get(id);
        if (epic != null) {
            return epic;
        } else if (subtask != null) {
            return subtask;
        }
        System.out.println("задачи с таким id нет");
        return null;
    }

    public void addTask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpic());
        epic.addSubtask(subtask);
        subtasks.put(subtask.hashCode(), subtask);
        subtask.setId(subtask.hashCode());
    }

    public void addTask(Epic epic) {
        epic.setId(epic.hashCode());
        epics.put(epic.hashCode(), epic);
        epic.setId(epic.hashCode());
    }

    public void updateTask(int id, Task task, int whatWasChanged) {
        if (epics.containsKey(id) || subtasks.containsKey(id)) {
            if (task.getClass() == Epic.class) {
                Epic epic = (Epic) task;
                epics.put(id, epic);
            } else if (task.getClass() == Subtask.class) {
                Subtask subtask = (Subtask) task;
                if (whatWasChanged == 3) {
                    changeTheStatus(subtask);
                }
                subtasks.put(id, subtask);
            }
        }else {
            System.out.println("задача с таким id не найдена");
        }
    }

    public void removeTask(int id) {
        epics.remove(id);
        subtasks.remove(id);
        System.out.println("Задача удалена");
    }

    public ArrayList<Subtask> getSubtasks(int id) {
        ArrayList<Subtask> subtasks = new ArrayList<>();

        if (epics.get(id) != null) {
            for (Subtask subtask : this.subtasks.values()) {
                if (subtask.getEpic() == id) {
                    subtasks.add(subtask);
                }
            }
            return subtasks;
        }
        System.out.println("эпика с таким id не найдено");
        return null;
    }

    private void changeTheStatus(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpic());

        ArrayList<Subtask> subtasks = epic.getSubtasks();

        if (allSubtasksHaveTheSameProgress(subtasks, Progress.NEW) || subtasks.isEmpty()) {
            epic.setProgress(Progress.NEW);
        } else if (allSubtasksHaveTheSameProgress(subtasks, Progress.DONE)) {
            epic.setProgress(Progress.DONE);
        } else {
            epic.setProgress(Progress.IN_PROGRESS);
        }
    }

    private boolean allSubtasksHaveTheSameProgress(ArrayList<Subtask> subtasks, Progress progress) {
        for (Subtask elSubtask : subtasks) {
            if (elSubtask.getProgress() != progress) {
                return false;
            }
        }
        return true;
    }
}


