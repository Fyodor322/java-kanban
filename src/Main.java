
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static TaskManager taskManager;

    public static void main(String[] args) {
        taskManager = new TaskManager();
        scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            String coomand = scanner.next();
            int id;
            switch (coomand) {
                case "1":
                    System.out.println(taskManager.getTasks().toString());
                    break;
                case "2":
                    taskManager.removeAllTasks();
                    break;
                case "3":
                    createTask(scanner);
                    break;
                case "4":
                    updateTask(scanner);
                    break;
                case "5":
                    System.out.println("Введите id задачи, которую нужно удалить: ");
                    id = scanner.nextInt();
                    taskManager.removeTask(id);
                    break;
                case "6":
                    System.out.println(taskManager.getTasks().toString());
                    System.out.println("введите ID эпика: ");
                    id = scanner.nextInt();
                    System.out.println(taskManager.getSubtasks(id));
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Такой команды нет");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Вывести список задач");
        System.out.println("2 - Удалить все задачи");
        System.out.println("3 - Создать задачу");
        System.out.println("4 - Обновить задачу");
        System.out.println("5 - Удалить задачу по ID");
        System.out.println("6 - Получить список всех подзадач эпика");
        System.out.println("7 - выход");
    }

    private static void createTask(Scanner scanner) {
        System.out.println("Введите название задачи: ");
        String name = scanner.nextLine();

        System.out.println("Введите описание задачи: ");
        String description = scanner.nextLine();

        System.out.println("хотите добавить подзадачи? (да/нет)");
        String command = scanner.nextLine();

        if (command.equals("да")) {
            System.out.println("задача добавлена");
            Epic epic = new Epic(name, description);
            System.out.println("введите подзадачи разделяя их enter");
            System.out.println("чтобы перестать вводить задачи, введите пустые строки в поля описания и названия новой задачи");

            String nameST;
            String descriptionST;

            for (int i = 1; true; i++) {
                System.out.println("введите название " + i + " подзадачи");
                nameST = scanner.nextLine();
                System.out.println("введите описание " + i + " подзадачи");
                descriptionST = scanner.nextLine();
                if (!nameST.isEmpty() && !descriptionST.isEmpty()) {
                    taskManager.addTask(new Subtask(nameST, descriptionST, epic), epic);
                    System.out.println("подзадача добавлена");
                } else {
                    taskManager.addTask(epic);
                    break;
                }
            }

        } else {
            Task task = new Task(name, description);
            taskManager.addTask(task);
            System.out.println("задача добавлена");
        }
    }

    private static void updateTask(Scanner scanner) {
        System.out.println("введите id задачи, которую нужно изменить: ");
        int id = scanner.nextInt();
        if (taskManager.getTask(id) != null) {
            Task task = taskManager.getTask(id);
            System.out.println("Что вы хотите изменить?");
            System.out.println("1.Название задачи");
            System.out.println("2.Описание задачи");
            System.out.println("3.Прогресс задачи");
            String command = scanner.next();
            String newParam;
            switch (command) {
                case "1":
                    System.out.println("Введите новое название задачи:");
                    newParam = scanner.next();
                    task.setName(newParam);
                    System.out.println("Название задачи успешно изменено");
                    break;
                case "2":
                    System.out.println("Введите новое описание задачи:");
                    newParam = scanner.next();
                    task.setDescription(newParam);
                    System.out.println("Описание задачи успешно изменено");
                    break;
                case "3":
                    System.out.println("Введите новый прогресс задачи (NEW, IN_PROGRESS, DONE):");
                    Progress newParamProgress = Progress.valueOf(scanner.next());
                    taskManager.changeTheStatus(task, newParamProgress);
                    System.out.println("Прогресс задачи успешно изменён");
                    break;
            }
            taskManager.updateTask(task);
            System.out.println("Задача обновлена");
        }

    }
}
