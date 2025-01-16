

import enums.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import tasks.Subtask;


class SubtaskTest {
    @Test
    void canNotMakeYourselfYourEpic() {
        Subtask subtask21 = new Subtask("Подзадача21", "Подзадача21", 2, Progress.NEW);
        subtask21.setId(1);
        Assertions.assertFalse(subtask21.setEpicId(1), "объект subtask нельзя сделать своим же эпиком");
    }
}