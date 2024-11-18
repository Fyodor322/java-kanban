import java.util.ArrayList;

public class TaskManager {
    final private ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void removeAllTasks(){
        if(tasks.removeAll(tasks)){
            System.out.println("Все задачи удалены");
        }else {
            System.out.println("Не удалось удалить все задачи");
        }
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void addTask(Subtask subtask){
        tasks.add(subtask);
    }

    public void addTask(Epic epic){
        tasks.add(epic);
    }

    public void updateTask(Task task){
        for (Task elTask : tasks){
            if (elTask.getId() == task.getId()){
                elTask = task;
            }
        }
    }

    public void removeTask(int id){
        for (Task elTask : tasks){
            tasks.removeIf(n -> (n.getId() == id));
        }
    }

    public ArrayList<Subtask> getSubtasks(int id){
        for (Task elEpic : tasks){
            if (elEpic.getClass() == Epic.class){
                if (elEpic.getId() == id){
                    return ((Epic) elEpic).getSubtasks();
                }
            }
        }
        System.out.println("эпика с таким id не найденно");
        return null;
    }

    public void changeTheStatus(Task task, Progress progress){
        task.setProgress(progress);
        if(task.getClass() == Subtask.class){
            Subtask subtask = (Subtask) task;
            ArrayList<Subtask> subtasks = subtask.getEpic().getSubtasks();

            if (allSubtasksHaveTheSameProgress(subtasks, Progress.NEW) || subtasks.isEmpty()){
                subtask.getEpic().setProgress(Progress.NEW);
            } else if (allSubtasksHaveTheSameProgress(subtasks, Progress.DONE)) {
                subtask.getEpic().setProgress(Progress.DONE);
            } else{
                subtask.getEpic().setProgress(Progress.IN_PROGRESS);
            }
        }
    }

    boolean allSubtasksHaveTheSameProgress(ArrayList<Subtask> subtasks, Progress progress){
        for(Subtask elSubtask : subtasks){
            if (elSubtask.getProgress() != progress) {
                return false;
            }
        }
        return true;
    }

}


