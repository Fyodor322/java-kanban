import java.util.ArrayList;

public class TaskManager {
    final private ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();

        Task task1 = new Task("Помыть посуду", "помыть за собой посуду");
        Task task2 = new Task("Заправить кровать", "заправить свою кровать");
        Epic epic1 = new Epic("Пойти гулять", "пойти гулять с друзьями");
        Subtask subtask11 = new Subtask("одеться", "одеться по погоде", epic1);
        Subtask subtask12 = new Subtask("выйти из дома ", "закрыть за собой дверь", epic1);
        Epic epic2 = new Epic("Сделать ДЗ", "сделать домашнее задание на завтра");
        Subtask subtask21 = new Subtask("Английский", "стр 100 номер 1", epic2);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(epic1);
        tasks.add(subtask11);
        tasks.add(subtask12);
        tasks.add(epic2);
        tasks.add(subtask21);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void removeAllTasks() {
        if (tasks.removeAll(tasks)) {
            System.out.println("Все задачи удалены");
        } else {
            System.out.println("Не удалось удалить все задачи");
        }
    }

    public Task getTask(int id) {
        for (Task elTask : tasks) {
            if (elTask.getId() == id) {
                return elTask;
            }
        }
        System.out.println("задачи с таким id нет");
        return null;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addTask(Subtask subtask, Epic epic) {
        epic.getSubtasks().add(subtask);
        tasks.add(subtask);
    }

    public void addTask(Epic epic) {
        tasks.add(epic);
    }

    public void updateTask(Task task) {
        for (Task elTask : tasks) {
            if (elTask.getId() == task.getId()) {
                elTask = task;
                return;
            }
        }
    }

    public void removeTask(int id) {

        if (tasks.removeIf(n -> (n.getId() == id))) {
            System.out.println("Задача удалена");
        } else {
            System.out.println("Не удалось удалить данную задачу");
        }

    }

    public ArrayList<Subtask> getSubtasks(int id) {
        for (Task elEpic : tasks) {
            if (elEpic.getClass() == Epic.class) {
                if (elEpic.getId() == id) {
                    return ((Epic) elEpic).getSubtasks();
                }
            }
        }
        System.out.println("эпика с таким id не найденно");
        return null;
    }

    public void changeTheStatus(Task task, Progress progress) {
        task.setProgress(progress);
        if (task.getClass() == Subtask.class) {
            Subtask subtask = (Subtask) task;
            ArrayList<Subtask> subtasks = subtask.getEpic().getSubtasks();

            if (allSubtasksHaveTheSameProgress(subtasks, Progress.NEW) || subtasks.isEmpty()) {
                subtask.getEpic().setProgress(Progress.NEW);
            } else if (allSubtasksHaveTheSameProgress(subtasks, Progress.DONE)) {
                subtask.getEpic().setProgress(Progress.DONE);
            } else {
                subtask.getEpic().setProgress(Progress.IN_PROGRESS);
            }
        }
    }

    boolean allSubtasksHaveTheSameProgress(ArrayList<Subtask> subtasks, Progress progress) {
        for (Subtask elSubtask : subtasks) {
            if (elSubtask.getProgress() != progress) {
                return false;
            }
        }
        return true;
    }

}


