import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    final private HashMap<Integer, Task> tasks;

    public TaskManager() {
        tasks = new HashMap<>();
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void removeAllTasks() {
        tasks.clear();
        System.out.println("Все задачи удалены");
    }

    public Task getTask(int id) {
        if (tasks.get(id) != null) {
            return tasks.get(id);
        }
        System.out.println("задачи с таким id нет");
        return null;
    }

    public void addTask(Task task) {
        tasks.put(task.hashCode(), task);
    }

    public void addTask(Subtask subtask) {
        Epic epic = (Epic) tasks.get(subtask.getEpic());
        epic.addSubtask(subtask);
        tasks.put(subtask.hashCode(), subtask);
        subtask.setId(subtask.hashCode());
    }

    public void addTask(Epic epic) {
        epic.setId(epic.hashCode());
        tasks.put(epic.hashCode(), epic);
    }

    public void updateTask(int id, Task task, int whatWasChanged) {
        if(whatWasChanged == 3){
            changeTheStatus(id, task);
        }
        tasks.put(id, task);
    }

    public void removeTask(int id) {
        tasks.remove(id);
        System.out.println("Задача удалена");
    }

    public ArrayList<Subtask> getSubtasks(int id) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        if (tasks.get(id) != null) {
            for (Task task : tasks.values()) {
                if (task.getClass() == Subtask.class) {
                    if (((Subtask) task).getEpic() == id) {
                        Subtask subtask = (Subtask) task;
                        subtasks.add(subtask);
                    }
                }
            }
            return subtasks;
        }
        System.out.println("эпика с таким id не найденно");
        return null;
    }

    private void changeTheStatus(int id, Task task) {
        Task oldTask = tasks.get(id);
        if (oldTask.getProgress().equals(task.getProgress())){
            if (task.getClass() == Subtask.class) {
                Subtask subtask = (Subtask) task;
                Epic epic = (Epic) tasks.get(subtask.getEpic());

                ArrayList<Subtask> subtasks = epic.getSubtasks();

                if (allSubtasksHaveTheSameProgress(subtasks, Progress.NEW) || subtasks.isEmpty()) {
                    epic.setProgress(Progress.NEW);
                } else if (allSubtasksHaveTheSameProgress(subtasks, Progress.DONE)) {
                    epic.setProgress(Progress.DONE);
                } else {
                    epic.setProgress(Progress.IN_PROGRESS);
                }
            }
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


