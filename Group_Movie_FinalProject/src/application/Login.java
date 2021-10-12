/** Login.java and Login.FXML made by Nicholas Que. 
 *  Login SQL and Validaiton collaborated by Stacy Rogers and Kim Lee.
 * Group Movie 2020. Modified 11/18 
 * 
 * **/
package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login {
	private final static String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;'|~`,?/*~$^+=<>]).{8,20}$"; //Make sure password is 8-20 length, 1 cap, 1 sym, 1 number
	private String passResults;
    @FXML
    private TitledPane menuTextTitle; //Window Pane Title.

    @FXML
    private AnchorPane anchorPane; //Anchor Pane

    @FXML
    private Button registerButton; //Button for registering

    @FXML
    private TextField userNameField; //User name text field

    @FXML
    private Button loginButton;	//Button for logging in

    @FXML
    private Button cancelQuitButton; //Button for quitting	

    @FXML
    private PasswordField passwordField; //Password text field. The special thing about this: it shows dots!
    
    @FXML
    private Label needAccountLabel; //It's just a Label: Displays User Name
    @FXML
    private Label passwordlabel; //It's just a Label: Displays Password
    @FXML
    private Label welcomeLabel; //It's just a Label: Displays Welcome 
    @FXML
    private Label EmailLabel;  //It's just a Label: Displays Email
    @FXML
    private Label captionLabel; //It's just a Label: Displays Caption beneath Welcome
    @FXML
    private Label userEmailValidation;
    @FXML
    private Label userPasswordValidation;
    
    @FXML
    void loginAccount(ActionEvent event) throws SQLException { //Event Handler that logs user in upon clicking Enter. Code by Stacy Rogers and supported by Kim Lee.	
    	//Create a connection to the database
		Connection con = null;
		 try {              
			 // registering Oracle driver class               
			 Class.forName("oracle.jdbc.driver.OracleDriver");               
			 
			 // getting connection               
			 con = DriverManager.getConnection(  "jdbc:oracle:thin:@oracle.gulfcoast.edu:1539:CLASS",             
					 							"Java6596", "Java296");              
			 // Test connection is null or not		 
			 if (con != null)                    
				 System.out.println("Connection established successfully!");
			 else                     
				 System.out.println("No Connection!");  
			 
			 
		
		String GetUserName = userNameField.getText();
		String GetUserPass = passwordField.getText();
		
			//Validate user name and check SQL database
			if (GetUserName.isEmpty())
				userEmailValidation.setText("Please enter your user.");
			else { 
			if (EmailValid(GetUserName)) {
				userNameField.setText(GetUserName);

				//SQL statement to check database
				Statement stmt = con.createStatement();
					try {
						
						String emailQuery = "SELECT email_address, password FROM mm_member "+
											"WHERE email_address = '" + GetUserName + "'" ;
						System.out.println(emailQuery);
						ResultSet email = stmt.executeQuery(emailQuery);
						
						//Display the contents of the result set
						while(email.next()) {
							System.out.println(email.getString("email_address"));
							userEmailValidation.setText("Success!");
							//System.out.println(email.getString("password"));
							passResults = email.getString("password"); 
							}
						
					}//end try
	
					catch(Exception e2) {			
					}
	
			} else {
				userEmailValidation.setText("Invalid username");
			}//end if/else EmailValid
			
			//Validate user password and check SQL database
			if(PassValid(GetUserPass)) {
				passwordField.setText(GetUserPass);
			
				if (passResults.equals(GetUserPass)) {
					System.out.println("Password validated");
					userPasswordValidation.setText("Success!");
				}
				else {
					System.out.println("Password does not match in Database");
				}
			}
				//SQL statement to check database
				
			 else if (!passResults.equals(GetUserPass)){
				userPasswordValidation.setText("Invalid password");
			}//end if/else PassValid
			}
		 } catch (ClassNotFoundException e1) {              
			 e1.printStackTrace();        } 
		 catch (SQLException e1) {               
			 e1.printStackTrace();        
			 
		 }//end catch
		 
		 finally{
			 try {
				 if(con!=null) con.close();	// close connection               
			 } catch (SQLException e1) {   
				 e1.printStackTrace();   
				 }//end catch     
		 }//end finally to close connection
		}//end ActionEvent


    @FXML
    void quitProgram(ActionEvent event) { //action for quitting program. This will quit the program. Duh.
    	Platform.exit();
    }

    @FXML
    void registerAccount(ActionEvent event) throws IOException { //action for registration. This will open Register.FXML
    		try {
    			
    			VBox rootContainer = (VBox) FXMLLoader.load(getClass().getClassLoader().getResource("Register.fxml"));
    			Scene scene = new Scene(rootContainer);
    			Stage primaryStage = new Stage();
				primaryStage.setScene(scene);
    			primaryStage.show();
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    }
    
  //Validate email
    public boolean EmailValid(String email) {
        String EValid = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(EValid);
    }//end EmailValid

    //Validate user password
    public static boolean PassValid(final String password) {
    	final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    	Matcher matcher = pattern.matcher(password);
    	return matcher.matches();
}
}


