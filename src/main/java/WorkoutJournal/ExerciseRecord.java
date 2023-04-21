package WorkoutJournal;

import java.util.ArrayList;
import java.util.List;

public record ExerciseRecord(){
    
    static List<String> heavyLoad = new ArrayList<>() {
        {
        add("Benchpress");
        add("Deadlift");
        add("Pullup");
        add("Squat");
        add("Shoulder press");
    }
    };

    static List<String> mediumLoad = new ArrayList<>() {
        {
        add("Reverse flys");
        add("Inclined press");
        add("Barbell rows");
        add("Pulldowns");
        add("Dips");
        add("Dumbbell press");
        add("Leg press");
        add("dumbbell lunges");
        
    }
    };

    static List<String> lightLoad = new ArrayList<>() {
        {
        add("Db front raises");
        add("Face pulls");
        add("Shrugs");
        add("Cable rows");
        add("Chest Flys");
        add("Calf raises");
        add("Bicep curls");
        add("tricep extensions");
        add("Skullcrushers");
        add("Hammercurls");
        add("Situps");
        add("Cable crunches");
        
    }
    };
}