package StandingPomodoro.app;

import StandingPomodoro.app.Controller.Startpage;
import StandingPomodoro.app.Service.ComponentService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class AppApplication extends Application{

	public static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(AppApplication.class, args);
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Startpage.fxml"));
		loader.setControllerFactory(AppApplication.context::getBean);
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
		stage.setTitle("Standing Pomodoro");
	}

	public ApplicationContext getContext(){
		return context;
	}
}
