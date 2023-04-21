package WorkoutJournal;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class WorkoutSession implements Serializable{
    private List<Exercise> workout;
    private String date;
    private User user;
    private static final long serialVersionUID = 1;

    public WorkoutSession(User user, String date){
        if (user != null && validateDate(date)){
            this.workout = new ArrayList<>();
            this.user = user;
            this.date = date;
        } else throw new IllegalArgumentException();
    }

    public static boolean validateDate(String date){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    public void addExercise(Exercise exercise) {
        workout.add(exercise);
    }

    public char calculateLoad() {
        String load = "LMH";
        int sum = 0;
        for (Exercise exercise : workout) {
            if (exercise.getLoad()=='H'){
                sum += 3;
            }
            if (exercise.getLoad()=='M'){
                sum += 2;
            }
            else {
                sum +=1;
            }
        }

        try {
            System.out.println(this.toString()+"before");
            sum = sum / workout.size();
            System.out.println(sum);
            return (char) load.charAt(sum - 1);
        } catch (IllegalStateException e) {
            System.out.println("kan ikke dele på null");
            return '\u0000';
        }
    }
    
    public void removeExercise(Exercise selectedExercise) {
        List<Exercise> newWorkout = new ArrayList<>();
        for (Exercise exercise : workout) {
            if (!(exercise==selectedExercise)) newWorkout.add(exercise);
        }
        workout = newWorkout;
        
    }
    
    public List<Exercise> getWorkout() {
        return new ArrayList<>(workout);
    }
    
    public String getDate() {
        return date;
    }
    
    public User getUser() {
        return user;
    }
    
}
