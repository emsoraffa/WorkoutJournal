package WorkoutJournal;


import java.time.LocalDate;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class WorkoutJournalTest {
    private HashMap<String,User> userData = WorkoutJournal.userData;
    private String today = LocalDate.now().toString();
    private static String filePath = "src/main/resources/workoutjournal/data/test.dat";
    

    @BeforeAll
    public static void selectTestData(){
        DataHandler.setFilePath(filePath);
        DataHandler.initialize();
        DataHandler.clear();
    }

    @Test
    @Order(1)
    @DisplayName("Try to create multiple workoutjournal instances")
    public void testWorkoutJournalSingleton(){
        WorkoutJournal instance1 = WorkoutJournal.getInstance();
        WorkoutJournal instance2 = WorkoutJournal.getInstance();
        Assertions.assertEquals(true, instance1==instance2);
    }

    @Test
    @Order(2)
    @DisplayName("create a user")
    public void testCreateUser(){
        WorkoutJournal.newUser("foo", "bar", "bar");
        Assertions.assertTrue(userData.containsKey("foo"));

    }

    @Test
    @Order(3)
    @DisplayName("create a session")
    public void testCreateSession(){
        WorkoutJournal.newSession(userData.get("foo"), today);
        Assertions.assertTrue(userData.get("foo").getSessions().size()==1);        
    }

    @Test
    @Order(4)
    @DisplayName("create an exercise")
    public void testCreateExercise(){
        Exercise test1 = new Exercise("Bench", 1, 1, 1, 'H', userData.get("foo").getSessions().get(today));
        WorkoutJournal.newExercise(test1, userData.get("foo").getSessions().get(today));
        Assertions.assertTrue(!userData.get("foo").getSessions().get(today).getWorkout().isEmpty());        
    }

    @Test
    @Order(5)
    @DisplayName("calculate load")
    public void testCalculateLoad(){
        Exercise test1 = new Exercise("Bench", 1, 1, 1, 'H', userData.get("foo").getSessions().get(today));
        Exercise test2 = new Exercise("Curls", 1, 1, 1, 'L', userData.get("foo").getSessions().get(today));
        WorkoutJournal.newExercise(test1, userData.get("foo").getSessions().get(today));
        WorkoutJournal.newExercise(test2, userData.get("foo").getSessions().get(today));
        Assertions.assertTrue(userData.get("foo").getSessions().get(today).calculateLoad()=='M');        
    }

}
