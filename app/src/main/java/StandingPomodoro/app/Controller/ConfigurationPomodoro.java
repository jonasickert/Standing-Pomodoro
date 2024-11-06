package StandingPomodoro.app.Controller;

import StandingPomodoro.app.AppApplication;
import StandingPomodoro.app.Entity.PomodoroEntity;
import StandingPomodoro.app.Service.RepositoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Log
public class ConfigurationPomodoro {

    private final RepositoryService repositoryService;
    private long activeId = -1;
    @Autowired
    public ConfigurationPomodoro(RepositoryService repositoryService){
        this.repositoryService = repositoryService;
    }

    @FXML
    public Text textEditPomodoro;
    @FXML
    public TextField inputConcentrationTime;
    @FXML
    public TextField inputShortBreak;
    @FXML
    public TextField inputLongBreak;
    @FXML
    public ComboBox<String> comboBox;
    @FXML
    public Button btnSave;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnBack;

    @FXML
    public void initialize(){
        inputConcentrationTime.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getControlNewText();
            return text.matches("\\d*") ? change : null;
        }));
        inputShortBreak.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getControlNewText();
            return text.matches("\\d*") ? change : null;
        }));
        inputLongBreak.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getControlNewText();
            return text.matches("\\d*") ? change : null;
        }));
        List<PomodoroEntity> entities = loadPomodoroConfig();;
        log.info(entities.toString());
        ObservableList<String> options = FXCollections.observableArrayList("new entity");
        for (PomodoroEntity entity: entities) {
            String info = entity.getConcentrationTime() + ", " + entity.getShortBreak() + ", " + entity.getLongBreak()
                    +", id: " + entity.getId();
            options.add(info);
        }
        log.info(options.toString());
        this.comboBox.setItems(options);
        this.comboBox.getSelectionModel().selectFirst();

        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal!="new entity"){
                String[] info = newVal.split(",");
                this.inputConcentrationTime.setText(info[0].trim());
                this.inputShortBreak.setText(info[1].trim());
                this.inputLongBreak.setText(info[2].trim());
                String[] id = info[3].split(":");
                this.activeId = Long.parseLong(id[1].trim());
            }
            else{
                this.inputConcentrationTime.setText("");
                this.inputShortBreak.setText("");
                this.inputLongBreak.setText("");
                this.activeId = -1;
            }
        });
    }

    @FXML
    public void savePomodoroConfig(ActionEvent event){
        try {
            if (inputConcentrationTime.getText()!=null && inputLongBreak != null && inputShortBreak != null) {
                PomodoroEntity entity = new PomodoroEntity(
                        Integer.parseInt(inputConcentrationTime.getText()),
                        Integer.parseInt(inputLongBreak.getText()),
                        Integer.parseInt(inputShortBreak.getText()));
                log.info(entity.toString());
                repositoryService.saveEntity(entity);
                backToStartpage(event);
            }
        }
        catch (Exception ex){
            log.info(ex.getMessage());
        }

    }

    public List<PomodoroEntity> loadPomodoroConfig(){
        return this.repositoryService.loadEntity();
    }

    @FXML
    public void deletePomodoroConfig(ActionEvent event){
        if (activeId!=-1){
            this.repositoryService.deleteEntity(activeId);
            this.initialize();
        }

    }

    @FXML
    public void backToStartpage(ActionEvent event) throws IOException {
        log.info("triggered");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Startpage.fxml"));
        loader.setControllerFactory(AppApplication.context::getBean);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public long getActiveId(){
        return activeId;
    }
}
