package StandingPomodoro.app.Controller;

import StandingPomodoro.app.AppApplication;
import StandingPomodoro.app.Service.ComponentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log
public class ConfigurationStanding {

    private final ComponentService componentService;


    @Autowired
    public ConfigurationStanding(ComponentService componentService){
        this.componentService = componentService;
    }

    @FXML
    public Button btnSaveAsStandard;
    @FXML
    public TextField inputSeatingTime;
    @FXML
    public TextField inputStandingTime;
    @FXML
    public Button btnLoad;
    @FXML
    public Button btnSave;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnBack;
    @FXML
    public Button btnTakeConfiguration;

    @FXML
    public void initialize(){
        inputSeatingTime.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getControlNewText();
            return text.matches("\\d*") ? change : null;
        }));
        inputStandingTime.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getControlNewText();
            return text.matches("\\d*") ? change : null;
        }));
    }

    @FXML
    public void takeConfiguration(ActionEvent event) throws IOException {
        if (inputStandingTime.getText()!="" && inputSeatingTime.getText()!="" && componentService.getNewStartPossible()){
            componentService.setStandingTimes(new Integer[]{Integer.parseInt(inputSeatingTime.getText()), Integer.parseInt(inputStandingTime.getText())});
            log.info(String.valueOf(componentService.getStandingTimes()[1]));
            goBackToStartpage(event);
        }

    }

    @FXML
    public void goBackToStartpage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Startpage.fxml"));
        loader.setControllerFactory(AppApplication.context::getBean);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
