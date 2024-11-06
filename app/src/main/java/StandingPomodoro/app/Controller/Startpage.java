package StandingPomodoro.app.Controller;

import StandingPomodoro.app.AppApplication;
import StandingPomodoro.app.Service.ComponentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.management.MXBean;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Log
@Component
public class Startpage {

    private final ComponentService componentService;


    private ScheduledFuture<?> pomodoroSchedul;
    private ScheduledFuture<?> standingSchedul;

    @Autowired
    private ThreadPoolTaskScheduler taskSchedulerPomodoro;
    @Autowired
    private ThreadPoolTaskScheduler taskSchedulerStanding;

    @Autowired
    public Startpage(ComponentService componentService){
        this.componentService = componentService;
    }
    @FXML
    public Button btnResetStanding;
    @FXML
    public Button btnStartStanding;
    @FXML
    public Text remainingTimePomodoro;
    @FXML
    public ProgressBar progressPomodoro;
    @FXML
    public Text remainingTimeStanding;
    @FXML
    public ProgressBar progressStanding;
    @FXML
    public Text configStanding;
    @FXML
    public Text configPomodoro;
    @FXML
    public Text textGreet;
    @FXML
    public Button btnConfigPomodoro;
    @FXML
    public Button btnStartPomodoro;
    @FXML
    public Button btnEndPomodoro;

    @FXML
    public void initialize(){
        Integer[] standingTimes = componentService.getStandingTimes();
        Integer[] pomodoroTimes = componentService.getPomodoroTimes();
        this.btnStartStanding.setDisable(!componentService.getNewStartPossible());
        this.btnResetStanding.setDisable(componentService.getNewStartPossible());
        this.configStanding.setText("seating:"+standingTimes[0]+", standing: "+standingTimes[1]);
        this.configPomodoro.setText("concentration: "+pomodoroTimes[0]+", short break: "+pomodoroTimes[1]+",\n long break: "+pomodoroTimes[2]);
        this.remainingTimeStanding.setText("00:00");
    }

    public void goToConfigurationPomodoro(ActionEvent event) throws IOException {
        setScene(event, "ConfigurationPomodoro.fxml");
    }

    @FXML
    public void goToConfigurationStanding(ActionEvent event) throws IOException {
        setScene(event, "ConfigurationStanding.fxml");
    }

    public void setScene(ActionEvent event, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(path));
        loader.setControllerFactory(AppApplication.context::getBean);
        Parent parent = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene start = new Scene(parent);
        stage.setScene(start);
    }

    private long startTime = new Date().getTime();
    private long endTime = new Date().getTime();

    @FXML
    public void startStanding(ActionEvent event){
        componentService.setNewStartPossible(false);
        this.btnStartStanding.setDisable(!componentService.getNewStartPossible());
        this.btnResetStanding.setDisable(componentService.getNewStartPossible());
        Integer[] times = this.componentService.getStandingTimes();
        int seatingTime = times[0];
        this.startTime = new Date().getTime();
        this.endTime = startTime+seatingTime*60000;
        seatingActive = true;
        startStandingTimer();
    }

    private void startStandingTimer(){
        standingSchedul = taskSchedulerStanding.scheduleAtFixedRate(this::updateStanding, 500);
    }

    private boolean seatingActive = true;

    private void updateStanding(){
        long remainingTime = endTime-(new Date().getTime());
        if (remainingTime<0){
            if (seatingActive){
                seatingActive = false;
                startTime=new Date().getTime();
                int standingTime = componentService.getStandingTimes()[1]*60000;
                endTime=startTime+standingTime;
                remainingTime = endTime-(new Date().getTime());
            }
            else {
                seatingActive = true;
                startTime=new Date().getTime();
                int standingTime = componentService.getStandingTimes()[0]*60000;
                endTime=startTime+standingTime;
                remainingTime = endTime-(new Date().getTime());
            }
        }
        String minutes = String.valueOf(new Date(remainingTime).getMinutes());
        String seconds = String.valueOf((new Date(remainingTime).getSeconds())%60);
        if (Float.parseFloat(seconds)/10<1){
            seconds = "0"+seconds;
        }
        if (Integer.parseInt(minutes)%10==0){
            minutes = "0"+minutes;
        }
        remainingTimeStanding.setText(minutes+":"+seconds);
        double percent = (double) ((new Date().getTime())-startTime)/(endTime-startTime);
        this.progressStanding.setProgress(percent);
    }

    @FXML
    public void resetStanding(ActionEvent event) throws IOException{
        if (standingSchedul != null) {
            standingSchedul.cancel(true);
            this.remainingTimeStanding.setText("00:00");
            componentService.setNewStartPossible(true);
            this.btnStartStanding.setDisable(!componentService.getNewStartPossible());
            this.btnResetStanding.setDisable(componentService.getNewStartPossible());
        }
    }

    private void startPomodoroTimer(){
        pomodoroSchedul = taskSchedulerPomodoro.scheduleAtFixedRate(this::updatePomodoro, 500);
    }

    private void updatePomodoro(){
        log.info("cripple");
    }





}
