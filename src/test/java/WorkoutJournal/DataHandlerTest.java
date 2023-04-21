package WorkoutJournal;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


public class DataHandlerTest {
    private HashMap<String,User> testUserData;
    private User testUser;
    private WorkoutSession testSession;
    private Exercise testExercise;
    private User savedUser;
    private static String filePath = "src/main/resources/workoutjournal/data/test.dat";

    @BeforeAll
    public static void selectTestData(){
        DataHandler.setFilePath(filePath);
        DataHandler.initialize();
        DataHandler.clear();
    }
    @BeforeEach
    public void setup(){
        //WorkoutJournal.getInstance();
        testUserData = new HashMap<String,User>();

        testUser = new User("foo", "abc", "abc");
        testUserData.put(testUser.getName(), testUser);

        testSession = new WorkoutSession(testUser, "2023-04-13");
        testUser.addSession(testSession.getDate(),testSession);

        testExercise = new Exercise("testExercise", 2, 3, 4, 'L',testSession);
        testSession.addExercise(testExercise);
        
    }

    @Test
    @Order(1)
    @DisplayName("Clear datafile")
    public void testClear() throws IOException{
        DataHandler.saveObject(testUserData);
        DataHandler.clear();
        HashMap<String, User> file = DataHandler.getObjects();
        HashMap<String, User> emptyHashmap = new HashMap<>();
        Assertions.assertEquals(emptyHashmap,file);
    }
        
    @Test
    @Order(1)
    @DisplayName("write object to file and read it")
    public void testSaveAndGetObject() throws IOException, ClassNotFoundException{
        DataHandler.saveObject(testUser);

        FileInputStream fileInput = new FileInputStream(filePath);
        ObjectInputStream stream = new ObjectInputStream(fileInput);
        Object obj = stream.readObject();
        if (obj instanceof User) {
            savedUser = (User) obj;
        } 
        Assertions.assertEquals(testUser.getName(), savedUser.getName(), "Teste å lagre et objekt og lese et objekt");
        stream.close();
        
        }

    }

