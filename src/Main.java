import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static TaskManager taskManager;

    public static void main(String[] args) {
        taskManager = new TaskManager();
        scanner = new Scanner(System.in);

        Task task1 = new Task("задача1", "опЗадачи1", Progress.NEW);
        Task task2 = new Task("задача2", "опЗадачи2", Progress.NEW);

        Epic epic1 = new Epic("епик1", "опЭпик1");
        Epic epic2 = new Epic("епик2", "опЭпик2");

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(epic1);
        taskManager.addTask(epic2);

        Subtask subtask11 = new Subtask("Подзадача11", "Подзадача11", epic1.getId());
        Subtask subtask21 = new Subtask("Подзадача21", "Подзадача21", epic2.getId());
        Subtask subtask22 = new Subtask("Подзадача22", "Подзадача212", epic2.getId());

        taskManager.addTask(subtask11);
        taskManager.addTask(subtask21);
        taskManager.addTask(subtask22);

        while (true) {
            printMenu();
            String command = scanner.next();
            switch (command) {
                case "1":
                    printAllTasks();
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
                    deleteTaskById(scanner);
                    break;
                case "6":
                    getSubtasksEpic(scanner);
                    break;
                case "7":
                    deleteAllSubtasksEpic(scanner);
                    break;
                case "8":
                    deleteSubtaskEpic(scanner);
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Такой команды нет");
                    break;
            }
        }
    }

    private static void printAllTasks() {
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
    }

    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Вывести список задач");
        System.out.println("2 - Удалить все задачи");
        System.out.println("3 - Создать задачу");
        System.out.println("4 - Обновить задачу");
        System.out.println("5 - Удалить задачу по ID");
        System.out.println("6 - Получить список всех подзадач эпика");
        System.out.println("7 - Удалить все подзадачи конкретного эпика");
        System.out.println("8 - Удалить подзадачу конкретного эпика");
        System.out.println("9 - выход");
    }

    private static void createTask(Scanner scanner) {
        System.out.println("Введите название задачи: ");
        String name = scanner.next();

        System.out.println("Введите описание задачи: ");
        String description = scanner.next();

        System.out.println("хотите добавить подзадачи? (да/нет)");
        String command = scanner.next();

        if (command.equals("да")) {
            System.out.println("задача добавлена");
            Epic epic = new Epic(name, description);
            taskManager.addTask(epic);
            System.out.println("введите подзадачи разделяя их enter");
            System.out.println("чтобы перестать вводить задачи, введите пустые строки в поля описания и названия новой задачи");

            String nameST;
            String descriptionST;
            scanner.nextLine();

            for (int i = 1; true; i++) {
                System.out.println("введите название " + i + " подзадачи");
                nameST = scanner.nextLine();
                System.out.println("введите описание " + i + " подзадачи");
                descriptionST = scanner.nextLine();
                if (!nameST.isEmpty() && !descriptionST.isEmpty()) {
                    taskManager.addTask(new Subtask(nameST, descriptionST, epic.getId()));
                    System.out.println("подзадача добавлена");
                } else {
                    break;
                }
            }

        } else {
            Task task = new Task(name, description, Progress.NEW);
            taskManager.addTask(task);
            System.out.println("задача добавлена");
        }
    }

    private static void updateTask(Scanner scanner) {
        System.out.println("введите id задачи, которую нужно изменить: ");
        int id = Integer.parseInt(scanner.next());
        if (taskManager.getTask(id) != null) {
            Task task = new Task(taskManager.getTask(id));

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
                    task.setProgress(newParamProgress);
                    System.out.println("Прогресс задачи успешно изменён");
                    break;
            }
            taskManager.updateTask(task);
            System.out.println("Задача обновлена");
        } else {
            System.out.println("Задача с таким id не найдена");
        }
    }

    private static void deleteTaskById(Scanner scanner) {
        System.out.println("Введите id задачи, которую нужно удалить: ");
        int id = scanner.nextInt();
        taskManager.removeTask(id);
    }

    private static void getSubtasksEpic(Scanner scanner) {
        printAllTasks();
        System.out.println("введите ID эпика: ");
        int id = scanner.nextInt();
        System.out.println(taskManager.getSubtasks(id));
    }

    private static void deleteAllSubtasksEpic(Scanner scanner) {
        System.out.println("Введите id эпика: ");
        int idEpic = scanner.nextInt();
        taskManager.clearEpicSubtasks(idEpic);
    }

    private static void deleteSubtaskEpic(Scanner scanner) {
        System.out.println("Введите id эпика: ");
        int idEpic = scanner.nextInt();
        System.out.println("Введите id подзадачи");
        int idSubtask = scanner.nextInt();
        taskManager.removeEpicSubtask(idEpic, idSubtask);
    }
}
