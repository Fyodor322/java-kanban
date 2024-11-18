import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static TaskManager taskManager;

    public static void main(String[] args) {
        taskManager = new TaskManager();
        scanner = new Scanner(System.in);
        while (true){
            printMenu();
            String coomand = scanner.nextLine();
            switch (coomand){
                case "1":
                    System.out.println(taskManager.getTasks().toString());
                    break;
                case "2":
                    taskManager.removeAllTasks();
                    break;
                case "3":
                    createTask(scanner);
                    break;
                case "7":
                    System.out.println(taskManager.getTasks().toString());
                    System.out.println("введите ID эпика: ");
                    int id = scanner.nextInt();
                    System.out.println(taskManager.getSubtasks(id));
            }
    }

    }
    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Вывести список задач");
        System.out.println("2 - Удалить все задачи");
        System.out.println("3 - Создать задачу");
        System.out.println("4 - Создать Эпик");
        System.out.println("5 - Обновить задачу");
        System.out.println("6 - Удалить задачу по ID");
        System.out.println("7 - Получить список всех подзадач эпика");
        System.out.println("8 - Изменить статус задачи");
        System.out.println("9 - выход");
    }
    private static void createTask(Scanner scanner){
        System.out.println("Введите название задачи: ");
        String name = scanner.nextLine();

        System.out.println("Введите описание задачи: ");
        String description = scanner.nextLine();

        System.out.println("хотите добавить подзадачи? (да/нет)");
        String command = scanner.nextLine();

        if (command.equals("да")){
            System.out.println("задача добавлена");
            Epic epic = new Epic(name, description);
            System.out.println("введите подзадачи разделяя их enter");
            System.out.println("чтобы перестать вводить задачи, введите пустые строки в поля описания и названия новой задачи");

            String subtask = " ";

            String nameST;
            String descriptionST;

                for (int i = 1; true; i++){
                    System.out.println("введите название " + i + " подзадачи");
                    nameST = scanner.nextLine();
                    System.out.println("введите описание " + i + " подзадачи");
                    descriptionST = scanner.nextLine();
                    if(!nameST.isEmpty() && !descriptionST.isEmpty()){
                        epic.getSubtasks().add(new Subtask(nameST, descriptionST, epic));
                        System.out.println("подзадача добавлена");
                    }else {
                        taskManager.addTask(epic);
                        break;
                    }
                }

        }else {
            Task task = new Task(name, description);
            taskManager.addTask(task);
            System.out.println("задача добавлена");
        }
    }
}
