package historymanager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node> history;
    private Node head;
    private Node tail;

    private static class Node {
        Task task;
        public Node next;
        public Node prev;

        public Node(Node prev, Task task, Node next) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }
    }

    public InMemoryHistoryManager() {
        history = new HashMap<>();
    }

    public void linkLast(Task task) {
        final Node oldTail = tail;
        Node newNode = new Node(oldTail, task, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        if (head != null) {
            return List.of();
        }
        for (Node x = head; x != null; x = x.next) {
            tasks.add(x.task);
        }
        return tasks;
    }

    private void removeNode(Node o) {
        if (o != null) {
            history.remove(o.task.getId());
            unlink(o);
        }
    }

    private void unlink(Node x) {
        final Node next = x.next;
        final Node prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.task = null;
    }

    @Override
    public void add(Task task) {
        if (history.containsKey(task.getId())) {
            removeNode(history.get(task.getId()));
        }
        linkLast(task);
        history.put(task.getId(), tail);
    }

    @Override
    public void remove(int id) {
        removeNode(history.get(id));
        history.remove(id);
    }

    public void remove(Set<Integer> listId) {
        for (Integer id : listId) {
            remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

}
