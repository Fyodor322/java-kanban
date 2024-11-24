import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Task> tasks;

    private int nextId = 1;

    public TaskManager() {
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        tasks = new HashMap<>();
    }

    public Collection<Subtask> getSubtasks() {
        return subtasks.values();
    }

    public Collection<Epic> getEpics() {
        return epics.values();
    }

    public Collection<Task> getTasks(){
        return tasks.values();
    }

    public void removeAllTasks() {
        tasks.clear();
        System.out.println("Все задачи удалены");
    }

    public void removeAllSubtasks(){
        for (Epic epic : epics.values()){
            epic.clearSubtasks();
            calculateProgress(epic);
        }
        subtasks.clear();
        System.out.println("Все подзадачи удалены");
    }

    public void removeAllEpics(){
        subtasks.clear();
        epics.clear();
        System.out.println("Все эпики удалены");
    }

    public Task getTask(int id){
        if(tasks.get(id) != null) {
            return tasks.get(id);
        }
        System.out.println("задача с таким id не найдена");
        return null;
    }

    public Subtask getSubtask(int id){
        if(subtasks.get(id) != null) {
            return subtasks.get(id);
        }
        System.out.println("задача с таким id не найдена");
        return null;
    }

    public Epic getEpic(int id){
        if(epics.get(id) != null) {
            return epics.get(id);
        }
        System.out.println("задача с таким id не найдена");
        return null;
    }

    public void addTask(Subtask subtask) {
        subtask.setId(nextId++);
        Epic epic = epics.get(subtask.getEpic());
        epic.addSubtask(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
        calculateProgress(epic);
    }

    public void addTask(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
    }

    public void addTask(Task task){
        task.setId(nextId++);
        tasks.put(task.getId(), task);
    }

    public void updateTask(Task task){
        int id = task.getId();
        if(epics.containsKey(id)){
            Epic epic = getEpic(id);
            epic.setName(task.getName());
            epic.setDescription(task.getDescription());
            epics.put(id, epic);
        } else if(tasks.containsKey(id)){
            tasks.put(id, task);
        } else if(subtasks.containsKey(id)){
            Subtask subtask = getSubtask(id);
            subtask.setName(task.getName());
            subtask.setDescription(task.getDescription());
            subtask.setProgress(task.getProgress());
            subtasks.put(id, subtask);
            calculateProgress(epics.get(subtask.getEpic()));
        } else{
            System.out.println("задача с таким id не найдена");
        }
    }

    public void removeTask(int id) {
        tasks.remove(id);
        System.out.println("Задача удалена");
    }

    public void removeSubtask(int id){
        Subtask subtask = subtasks.remove(id);
        Epic epic = epics.get(subtask.getEpic());
        epic.removeSubtask(id);
        calculateProgress(epic);
        System.out.println("Задача удалена");
    }

    public void removeEpic(int id){
        for (Integer subtaskId : epics.get(id).getSubtasks()){
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
        System.out.println("Задача удалена");
    }

    public ArrayList<Subtask> getSubtasks(int id) {
        ArrayList<Subtask> subtasks = new ArrayList<>();

        if (epics.get(id) != null) {
            for (Integer subtask : epics.get(id).getSubtasks()) {
                subtasks.add(this.subtasks.get(subtask));
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


