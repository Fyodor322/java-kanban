import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EpicTest {
    @Test
    void addYourselfToYourSubtasks(){
        Epic epic1 = new Epic("эпик1", "опЭпик1");
        epic1.setId(1);
        epic1.addSubtask(1);
        Assertions.assertFalse(epic1.getSubtasks().contains(1), "объект Эпик нельзя добавить в самого себя в виде подзадачи");
    }
}