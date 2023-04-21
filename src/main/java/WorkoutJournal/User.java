package WorkoutJournal;


import java.io.Serializable;
import java.util.HashMap;
import java.util.regex.*;


public class User implements Serializable{
    
    private String name;
    private String password;
    private HashMap<String,WorkoutSession> sessions= new HashMap<>();
    private static final long serialVersionUID = 2;


    public User(String name, String password1, String password2) {
        if (!validateNewName(name)) throw new IllegalArgumentException("The name is not valid");
        if (!validateNewPassword(password1, password2)) throw new IllegalArgumentException("The password is not valid");
        this.name = name;
        this.password = password1;
    }

    public HashMap<String, WorkoutSession> getSessions() {
        return sessions;
    }

    public void addSession(String date, WorkoutSession session){
        if (session == null || !WorkoutSession.validateDate(date)) throw new IllegalArgumentException();
        
        sessions.put(date, session);
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public static boolean validateNewName(String name){
        if (name == null) return false;

        //Regex som godtar alle stringer med bare store eller små bokstaver.
        String regex = "^[A-Za-z]\\w{1,29}$"; 
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        
        //Returnerer om strengen name inneholder bare store eller små bokstaver.
        return matcher.matches();
    }

    public boolean validateNewPassword(String password1, String password2){
        if (password1 == null || password2 == null) return false;
        if (!password1.equals(password2)) return false;
        
        //Iterer gjennom passordet og sjekker returnerer false hvis det inneholder noe annet en tall og bokstaver.
        char[] charArr = password1.toCharArray();
        for (char c : charArr) {
            if (Character.isDigit(c) || Character.isLetter(c)) return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "User [name=" + name + ", sessions=" + sessions + "]";
    }
}
