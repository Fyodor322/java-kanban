public class Managers {
    static public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
