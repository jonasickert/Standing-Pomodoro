package StandingPomodoro.app.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ComponentService {

    private Integer[] pomodoroTimes;
    private Integer[] standingTimes;
    private boolean newStandingStartPossible;

    public ComponentService(){
        pomodoroTimes = new Integer[]{25,15,10};
        standingTimes = new Integer[]{45,15};
        newStandingStartPossible = true;
    }

    private static class PomodoroInfo {
        private int concentration;
        private int shortBreak;
        private int longBreak;
        public PomodoroInfo(int concentration, int shortBreak, int longBreak){
            this.concentration = concentration;
            this.shortBreak = shortBreak;
            this.longBreak = longBreak;
        }
        public int getConcentrationDuration(){
            return concentration;
        }
        public int getShortBreak() {
            return shortBreak;
        }
        public int getLongBreak() {
            return longBreak;
        }
    }

    public Integer[] getStandingTimes() {
        return standingTimes;
    }

    public void setStandingTimes(Integer[] times){
        this.standingTimes = times;
    }

    public void setNewStandingStartPossible(boolean seating){
        newStandingStartPossible = seating;
    }

    public boolean getNewStandingStartPossible(){
        return newStandingStartPossible;
    }

    public PomodoroInfo setNewPomodoroTimer(int concentration, int shortBreak, int longBreak){
        return new PomodoroInfo(concentration, shortBreak, longBreak);
    }





}
