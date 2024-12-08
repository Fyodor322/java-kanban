package historyManager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history;
    private static final int SIZE = 10;

    public InMemoryHistoryManager() {
        history = new ArrayList<>(SIZE);
    }

    @Override
    public void add(Task task){
        if (history.size() == SIZE){
            history.removeFirst();
            history.add(task);
        }else {
            history.add(task);
        }
    }

    @Override
    public void remove(Task task){
        history.remove(task);
    }

    @Override
    public void removeAllEpics() {
        history.removeIf(task -> task instanceof Epic);
    }

    @Override
    public void removeAllSubtasks() {
        history.removeIf(task -> task instanceof Subtask);
    }

    @Override
    public void removeAllTasks() {
        history.removeIf(task -> !(task instanceof Epic) && !(task instanceof Subtask));
    }

    @Override
    public List<Task> getHistory(){
        return history;
    }
}
