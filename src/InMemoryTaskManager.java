import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Task> tasks;
    private final ArrayList<Task> history;

    private int nextId = 1;

    public InMemoryTaskManager() {
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        tasks = new HashMap<>();
        history = new ArrayList<>(10);
    }

    @Override
    public Collection<Subtask> getSubtasks() {
        return subtasks.values();
    }

    @Override
    public Collection<Epic> getEpics() {
        return epics.values();
    }

    @Override
    public Collection<Task> getTasks(){
        return tasks.values();
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
        System.out.println("Все задачи удалены");
    }

    @Override
    public void removeAllSubtasks(){
        for (Epic epic : epics.values()){
            epic.clearSubtasks();
            calculateProgress(epic);
        }
        subtasks.clear();
        System.out.println("Все подзадачи удалены");
    }

    @Override
    public void removeAllEpics(){
        subtasks.clear();
        epics.clear();
        System.out.println("Все эпики удалены");
    }

    @Override
    public Task getTask(int id){
        if(tasks.get(id) != null) {
            addHistory(tasks.get(id));
            return tasks.get(id);
        }
        System.out.println("задача с таким id не найдена");
        return null;
    }

    @Override
    public Subtask getSubtask(int id){
        if(subtasks.get(id) != null) {
            addHistory(subtasks.get(id));
            return subtasks.get(id);
        }
        System.out.println("задача с таким id не найдена");
        return null;
    }

    @Override
    public Epic getEpic(int id){
        if(epics.get(id) != null) {
            addHistory(epics.get(id));
            return epics.get(id);
        }
        System.out.println("задача с таким id не найдена");
        return null;
    }

    @Override
    public void addTask(Subtask subtask) {
        subtask.setId(nextId++);
        Epic epic = epics.get(subtask.getEpic());
        epic.addSubtask(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
        calculateProgress(epic);
    }

    @Override
    public void addTask(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addTask(Task task){
        task.setId(nextId++);
        tasks.put(task.getId(), task);
    }

    @Override
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

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
        System.out.println("Задача удалена");
    }

    @Override
    public void removeSubtask(int id){
        Subtask subtask = subtasks.remove(id);
        Epic epic = epics.get(subtask.getEpic());
        epic.removeSubtask(id);
        calculateProgress(epic);
        System.out.println("Задача удалена");
    }

    @Override
    public void removeEpic(int id){
        for (Integer subtaskId : epics.get(id).getSubtasks()){
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
        System.out.println("Задача удалена");
    }

    @Override
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

    @Override
    public Collection<Task> getHistory(){
        return history;
    }

    private void addHistory(Task task){
        if (history.size() >= 10){
            history.removeFirst();
            history.add(task);
        }else {
            history.add(task);
        }
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


