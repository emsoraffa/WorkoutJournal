package WorkoutJournal;

import java.io.*;
import java.util.HashMap;


public class DataHandler {
    private static String filePath = "src/main/resources/workoutjournal/data/data.dat";
    private static HashMap<String,User> userData = new HashMap<String,User>();

    public static void initialize(){
        try {
            File dataFile = new File(filePath);
            dataFile.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static void saveObject(Serializable object){
       
        try {
            FileOutputStream fileOutput = new FileOutputStream(filePath);
            ObjectOutputStream output = new ObjectOutputStream(fileOutput);

            output.writeObject(object);
            output.close();
            
        } catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, User> getObjects(){

    try {
        FileInputStream fileInput = new FileInputStream(filePath);
        ObjectInputStream stream = new ObjectInputStream(fileInput);
        
        while (true){
            Object obj = stream.readObject();
            
            if (obj == null) break;
            if (obj instanceof HashMap){
                userData =((HashMap<String,User>)obj);
            }
        }

        stream.close();
        return userData;

    } catch (EOFException e){
        System.out.println("-all files read-");
        System.out.println("Saved Users: ");
        for (User user : userData.values()) {
            System.out.println(user.getName());
        }
        return userData;
    }

    catch (Exception e){
        System.out.println(e.getLocalizedMessage());
    }

    return null;
    }

    //clears content of data.dat
    public static void clear(){
        try {
            new FileWriter(filePath, false).close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static void setFilePath(String path){
        filePath = path;
    }

    public static void main(String[] args) {
        HashMap<String, User> testdata = new HashMap<>();
        User emil = new User("Emil", "abc", "abc");
        testdata.put("Emil", emil);
        WorkoutSession testøkt = new WorkoutSession(emil, "2023-04-13");
        System.out.println(testøkt.getDate());
        emil.addSession(testøkt.getDate(),testøkt);
        Exercise benken = new Exercise("Benkpress", 2, 3, 4, 'L',testøkt);
        testøkt.addExercise(benken);
        DataHandler.saveObject(emil);
        
        System.out.println("final: " + DataHandler.getObjects().get("Emil").getSessions());
    }

   

}
    

