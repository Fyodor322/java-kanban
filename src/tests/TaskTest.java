package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.*;
import enums.*;

class TaskTest {
    @Test
    void equalIfTheIdIsEqual() {
        Task task1 = new Task("задача1", "опЗадачи1", Progress.NEW);
        Task task2 = new Task("задача2", "опЗадачи2", Progress.NEW);
        task1.setId(1);
        task2.setId(1);
        Assertions.assertEquals(task1, task2, "объекты должны быть равны, если равен их id");
    }

    @Test
    void TheHeirsAreEqualIfTheIdIsEqual() {
        Task epic1 = new Epic("задача1", "опЗадачи1");
        Task epic2 = new Epic("задача2", "опЗадачи2");
        epic1.setId(1);
        epic2.setId(1);
        Assertions.assertEquals(epic1, epic2, "наследники должны быть равны, если равен их id");
    }
}