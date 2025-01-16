package history_manager;

import tasks.Task;

import java.util.*;


public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node> history;
    private Node head;
    private Node tail;

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
            for (Node x = head; x != null; x = x.next) {
                tasks.add(x.task);
            }
        }

        return tasks;
    }

    private void removeNode(Node o) {
        if (o == null) {
            for (Node x = head; x != null; x = x.next) {
                if (x.task == null) {
                    unlink(x);
                }
            }
        } else {
            for (Node x = head; x != null; x = x.next) {
                if (x.task.equals(o.task)) {
                    unlink(x);
                }
            }
        }
    }

    private void unlink(Node x) {
        final Task element = x.task;
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
        removeNode(new Node(tail, task, null));
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
