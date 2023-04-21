package WorkoutJournal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class WorkoutJournal {
    private static WorkoutJournal wjInstance = null;
    public static HashMap<String,User> userData = new HashMap<String,User>();
    private static HashMap<User,LocalDate> activeSession;

    private WorkoutJournal(){
        try {
            userData = DataHandler.getObjects();
        } catch (NullPointerException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static synchronized WorkoutJournal getInstance(){
        if (wjInstance==null){

            wjInstance= new WorkoutJournal();
        }

        return wjInstance;
    }

    public static List<User> getUserData(){
        List<User> userlist = new ArrayList<User>(userData.values());
        return userlist;
    }
    
    public static void setActiveSession(String username, LocalDate date){
        if (userData.get(username)==null) throw new IllegalArgumentException();
        activeSession = new HashMap<>();
        activeSession.put(userData.get(username), date);
    }

    public static User getActiveUser(){
        for (User key : activeSession.keySet()) {
            return key;
        }
        return null;
    }

    public static LocalDate getActiveDate(){

        return activeSession.get(getActiveUser());
    }
    
    public static void newUser(String name, String password1, String password2){
        User newUser = new User(name, password1, password2);
        WorkoutJournal.newSession(newUser, LocalDate.now().toString());
        userData.put(newUser.getName(),newUser);
        WorkoutJournal.save();
    }

    public static void newExercise(Exercise exercise,WorkoutSession session){
        session.addExercise(exercise);
        WorkoutJournal.save();
    }

    public static void deleteExercises(Exercise...exercises){
        for (Exercise exercise : exercises) {
            exercise.getSession().removeExercise(exercise);
        }
        
        WorkoutJournal.save();
    }

    public static void generateExercises() {
        List<Exercise> workout = new ArrayList<>();

        workout.add(new Exercise(ExerciseRecord.heavyLoad.get(ThreadLocalRandom.current().nextInt(0, 5)),5,5,0,'H', getActiveUser().getSessions().get(getActiveDate().toString())));
        workout.add(new Exercise(ExerciseRecord.heavyLoad.get(ThreadLocalRandom.current().nextInt(0, 5)),5,5,0,'H', getActiveUser().getSessions().get(getActiveDate().toString())));
        workout.add(new Exercise(ExerciseRecord.mediumLoad.get(ThreadLocalRandom.current().nextInt(0, 8)),4,12,0,'M', getActiveUser().getSessions().get(getActiveDate().toString())));
        workout.add(new Exercise(ExerciseRecord.mediumLoad.get(ThreadLocalRandom.current().nextInt(0, 8)),4,12,0,'M', getActiveUser().getSessions().get(getActiveDate().toString())));
        workout.add(new Exercise(ExerciseRecord.lightLoad.get(ThreadLocalRandom.current().nextInt(0, 12)),3,15,0,'L', getActiveUser().getSessions().get(getActiveDate().toString())));
        workout.add(new Exercise(ExerciseRecord.lightLoad.get(ThreadLocalRandom.current().nextInt(0, 12)),3,15,0,'L', getActiveUser().getSessions().get(getActiveDate().toString())));
        workout.add(new Exercise(ExerciseRecord.lightLoad.get(ThreadLocalRandom.current().nextInt(0, 12)),3,15,0,'L', getActiveUser().getSessions().get(getActiveDate().toString())));

        for (Exercise exercise : workout) {
            newExercise(exercise, getActiveUser().getSessions().get(getActiveDate().toString()));
        }
    }

    public static void newSession(User user, String date){
        WorkoutSession newSession = new WorkoutSession(user, date);
        user.addSession(date, newSession);
        WorkoutJournal.save();
    }

    //Overwrites data.dat and stores a list of user objects
    private static void save(){
        
        DataHandler.clear();

        DataHandler.saveObject(userData);
    }

    
}



