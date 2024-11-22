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

        Subtask subtask11 = new Subtask("Подзадача11", "Подзадача11", epic1.getId(), Progress.NEW);
        Subtask subtask21 = new Subtask("Подзадача21", "Подзадача21", epic2.getId(), Progress.NEW);
        Subtask subtask22 = new Subtask("Подзадача22", "Подзадача212", epic2.getId(), Progress.NEW);

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
                    taskManager.removeAllSubtasks();
                    taskManager.removeAllEpics();
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
        System.out.println("7 - выход");
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
                    taskManager.addTask(new Subtask(nameST, descriptionST, epic.getId(), Progress.NEW));
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
        System.out.println("что вы хотите изменить?");
        System.out.println("1.Задачу");
        System.out.println("2.Подзадачу");
        System.out.println("3.Эпик");
        int typeTask = Integer.parseInt(scanner.next());
        if(typeTask == 1){
            Task task = new Task(taskManager.getTask(id).getName(), taskManager.getTask(id).getDescription(), Progress.NEW);
            task.setId(id);
            changeTask(scanner, task, id);
        }else if (typeTask == 2){
            Subtask subtask = new Subtask(taskManager.getSubtask(id).getName(), taskManager.getSubtask(id).getDescription(), taskManager.getSubtask(id).getEpic(), Progress.NEW);
            subtask.setId(id);
            changeTask(scanner, subtask, id);
        } else if (typeTask == 3) {
            Epic epic = new Epic(taskManager.getEpic(id).getName(), taskManager.getEpic(id).getDescription());
            epic.setId(id);
            changeTask(scanner, epic, id);
        }else {
            System.out.println("такой команды нет");
        }
    }

    private static void deleteTaskById(Scanner scanner) {
        System.out.println("что вы хотите удалить?");
        System.out.println("1.Задачу");
        System.out.println("2.Подзадачу");
        System.out.println("3.Эпик");
        int typeTask = Integer.parseInt(scanner.next());
        System.out.println("Введите id задачи, которую нужно удалить: ");
        int id = scanner.nextInt();
        if (typeTask ==1){
            taskManager.removeTask(id);
        } else if (typeTask == 2) {
            taskManager.removeSubtask(id);
        } else if (typeTask == 3) {
            taskManager.removeEpic(id);
        }else {
            System.out.println("такой команды нет");
        }

    }

    private static void getSubtasksEpic(Scanner scanner) {
        printAllTasks();
        System.out.println("введите ID эпика: ");
        int id = scanner.nextInt();
        System.out.println(taskManager.getSubtasks(id));
    }

    private static void changeTask(Scanner scanner, Epic task, int id){
        if (taskManager.getEpic(id) != null) {

            System.out.println("Что вы хотите изменить?");
            System.out.println("1.Название задачи");
            System.out.println("2.Описание задачи");
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
            }
            taskManager.updateTask(task);
            System.out.println("Задача обновлена");
        } else {
            System.out.println("Задача с таким id не найдена");
        }
    }

    private static void changeTask(Scanner scanner, Subtask task, int id){
        if (taskManager.getSubtask(id) != null) {

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

    private static void changeTask(Scanner scanner, Task task, int id){
        if (taskManager.getTask(id) != null) {

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
}
