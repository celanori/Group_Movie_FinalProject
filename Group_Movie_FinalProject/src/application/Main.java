package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
//import javafx.scene.layout.BorderPane;



public class Main extends Application {
	 @Override
	public void start(Stage primaryStage) throws Exception{ 
		try {
			VBox rootContainer = (VBox) FXMLLoader.load(getClass().getClassLoader().getResource("Register.fxml")); //FOR TESTING FXMLS, SIMPLY TYPE THE FXML FILE NAME IN THE QUOTES.
			//To add code into the Scenes, go to their respective .java classes.
			Scene scene = new Scene(rootContainer);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	public static void main(String[] args) {
		launch(args);
	}

}
