package WorkoutJournal;

import java.io.Serializable;

public class Exercise implements Comparable<Exercise>, Serializable{

    private String name;
    private int sets;
    private int reps;
    private int weight;
    private char load;
    private WorkoutSession session;
    private static final long serialVersionUID = 0;

    public Exercise(String name, int sets, int reps, int weight, char i, WorkoutSession session){
        if (validateExercise(name, sets, reps, weight, i) && session!=null){
            this.name = name;
            this.sets = sets;
            this.reps = reps;
            this.weight = weight;
            this.load = i;
            this.session=session;
        }
        else {
            throw new IllegalArgumentException("Feil!");
        }
    }

    public boolean validateExercise(String name, int sets, int reps, int weight, char load){
        if (name != null && sets > 0 && reps > 0 && weight >= 0 &&
        load == 'H' || load == 'M' || load == 'L'){
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Exercise exercise){
        final String index = "LMH";
        if (exercise.getLoad() != this.getLoad()){
            return index.indexOf(this.getLoad()) - index.indexOf(exercise.getLoad());
        }
        return (this.getReps() + this.getSets()) - (exercise.getReps() + exercise.getSets());
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSets() {
        return sets;
    }
    public void setSets(int sets) {
        this.sets = sets;
    }
    public int getReps() {
        return reps;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public char getLoad() {
        return load;
    }
    public void setLoad(char load) {
        this.load = load;
    }
    public WorkoutSession getSession(){
        return session;
    }
}
