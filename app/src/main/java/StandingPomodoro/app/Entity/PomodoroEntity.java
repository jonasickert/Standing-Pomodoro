package StandingPomodoro.app.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PomodoroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    int concentrationTime;
    int shortBreak;
    int longBreak;

    public PomodoroEntity(){
    }

    public PomodoroEntity(int con, int shortB, int longB){
        this.concentrationTime = con;
        this.longBreak = longB;
        this.shortBreak = shortB;
    }

    public long getId() {
        return id;
    }
    public int getConcentrationTime() {
        return concentrationTime;
    }
    public int getShortBreak() {
        return shortBreak;
    }
    public int getLongBreak() {
        return longBreak;
    }

}
