import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final ArrayList<Task> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>(10);
    }

    @Override
    public void add(Task task){
        if (history.size() == 10){
            history.removeFirst();
            history.add(new Task(task.getName(), task.getDescription(), task.getProgress()));
        }else {
            history.add(new Task(task.getName(), task.getDescription(), task.getProgress()));
        }
    }

    @Override
    public List<Task> getHistory(){
        return history;
    }
}
