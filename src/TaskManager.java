import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Task> tasks;

    public TaskManager() {
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        tasks = new HashMap<>();
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Task> getTasks(){return tasks;}

    public void removeAllTasks() {
        subtasks.clear();
        epics.clear();
        tasks.clear();
        System.out.println("Все задачи удалены");
    }

    public Task getTask(int id){
        if(tasks.get(id) != null){
            return tasks.get(id);
        } else if (subtasks.get(id) != null) {
            return subtasks.get(id);
        } else if (epics.get(id) != null) {
            return epics.get(id);
        }
        System.out.println("задача с таким id не найдена");
        return null;
    }

    public void addTask(Subtask subtask) {
        subtask.setId(subtask.hashCode());
        Epic epic = epics.get(subtask.getEpic());
        epic.addSubtask(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
    }

    public void addTask(Epic epic) {
        epic.setId(epic.hashCode());
        epics.put(epic.getId(), epic);
    }

    public void addTask(Task task){
        task.setId(task.hashCode());
        tasks.put(task.getId(), task);
    }

    public void updateTask(Task task){
        int id = task.getId();
        if(epics.containsKey(id)){
            Epic epic = new Epic(task, epics.get(id).getSubtasks());
            epics.put(id, epic);
        } else if(tasks.containsKey(id)){
            tasks.put(id, task);
        } else if(subtasks.containsKey(id)){
            Subtask subtask = new Subtask(task, subtasks.get(id).getEpic());
            subtasks.put(id, subtask);
            calculateProgress(epics.get(subtask.getEpic()));
        } else{
            System.out.println("задача с таким id не найдена");
        }
    }

    public void removeTask(int id) {
        for (Integer subtaskId : epics.get(id).getSubtasks()){
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
        subtasks.remove(id);
        tasks.remove(id);
        System.out.println("Задача удалена");
    }

    public void removeEpicSubtask(int idEpic, int idSubtask){
        subtasks.remove(idSubtask);
        epics.get(idEpic).removeSubtask(idSubtask);
    }

    public void clearEpicSubtasks(int idEpic){
        for (Integer idSubtask : epics.get(idEpic).getSubtasks()){
            subtasks.remove(idSubtask);
        }
        epics.get(idEpic).clearSubtasks();
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

    private void calculateProgress(Epic epic) {

        ArrayList<Subtask> subtasks = getSubtasks(epic.getId());

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


