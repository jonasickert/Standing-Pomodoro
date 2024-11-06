package StandingPomodoro.app.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

@Service
public class ComponentService {

    private Integer[] pomodoroTimes;
    private Integer[] standingTimes;
    private boolean newStartPossible;

    public ComponentService(){
        pomodoroTimes = new Integer[]{25,15,10};
        standingTimes = new Integer[]{45,15};
        newStartPossible = true;
    }

    public Integer[] getPomodoroTimes() {
        return pomodoroTimes;
    }

    public Integer[] getStandingTimes() {
        return standingTimes;
    }

    public void setPomodoroTimes(Integer[] times){
        this.pomodoroTimes = times;
    }

    public void setStandingTimes(Integer[] times){
        this.standingTimes = times;
    }

    public void setNewStartPossible(boolean seating){
        newStartPossible = seating;
    }

    public boolean getNewStartPossible(){
        return newStartPossible;
    }
}
