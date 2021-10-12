/** Register.java and Register.FXML designed and updated by Nicholas Que. Modified on 11/18/2020. 
 * Group M: Movies!
 */
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Register {
	private final static String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;'|~`,?/*~$^+=<>]).{8,20}$"; //Regex for Password: Make sure password is 8-20 length, 1 cap, 1 sym, 1 number
	private final static String EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"; //Regex for EMAIL: checks that user entered @[insert mail].com.  
	private final static int CVV_MAX = 3; //Constant for max length for CVV Code
	private final static int CREDIT_MAX = 16; //Constant for max length for credit card number
	private String paymentID; //PaymentID for holding information referencing buttons 
	boolean firstNameValid;
	boolean lastNameValid;
	boolean buttonValid;
	boolean cardNumberValid;
	boolean cardDateValid;
	boolean cvvCodeValid;
	boolean passwordValid;
	boolean confirmPasswordValid;
	boolean emailValid;
	int finalID;
	String firstName;
	String lastName;
	String movieID;
	String cardNumber;
	LocalDate cardDate;
	String cvvCode;
	String getPassword;
	String getConfirm;
	String getEmail;
	//titled and anchor pane
	@FXML
    private TitledPane redflixTitledPane;
    @FXML
    private AnchorPane clearButton;
    //labels
    @FXML
    private Label redflixTitle, captionLabel, firstnameLabel, lastnameLabel, paymentLabel, cardNumberLabel, passwordLabel, confirmPasswordLabel, emailLabel, expDateLabel, cvvCodeLabel, buttonValidation, lastNameValidation, firstNameValidation, cardNumberValidation, cardDateValidation, cvvValidation, passwordValidation, confirmPasswordValidation, emailValidation, passwordInstructionsLabel, determineValidation;

    //text fields for first and last name
    @FXML
    private TextField firstnameField; 
    @FXML
    private TextField lastnameField;
    //toggle buttons for payment methods
    @FXML
    private ToggleGroup group;
    @FXML
    private ToggleButton debitButton;
    @FXML
    private ToggleButton creditButton;
    //text and password fields for all input
    @FXML
    private PasswordField cardNumberField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField cvvtextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    //buttons for confirming, validating, quitting, and clearing input
    @FXML
    private Button confirmButton;
    @FXML
    private Button clearInputButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button validateInput;
    //date picker for expiration date
    @FXML
    private DatePicker dateField;
    @FXML
    void setMethodtoCredit(ActionEvent event) {
    	if (creditButton.isSelected()) {
    		paymentID = "2";
    	}
    } //end setMethodtoCredit
    @FXML
    void setMethodtoDebit(ActionEvent event) {
    	if (debitButton.isSelected()) {
    		paymentID = "5";
    	}
    } //end setMethodtoDebit
    
    @FXML
    void clearInput(ActionEvent event) { //clears all input for cleaning.
    	firstnameField.setText("");
    	lastnameField.setText("");
    	creditButton.setSelected(false);
    	debitButton.setSelected(false);
    	cardNumberField.setText("");
    	cvvtextField.setText("");
    	emailTextField.setText("");
    	passwordField.setText("");
    	confirmPasswordField.setText("");
    	firstNameValidation.setText("");
    	lastNameValidation.setText("");
    	buttonValidation.setText("");
    	cardNumberValidation.setText("");
    	cardDateValidation.setText("");
    	cvvValidation.setText(""); 
    	passwordValidation.setText("");
    	confirmPasswordValidation.setText("");
    	emailValidation.setText("");
    	confirmButton.setVisible(false);
    } //end clearInput
    @FXML 
    void validate(ActionEvent event) { //validates all input
    	if (firstnameField.getText().isEmpty()) {
    		firstNameValidation.setText("Please enter your first name.");
    	}
    	else if (!firstnameField.getText().isEmpty()) {
    		String toProperFirstName = firstnameField.getText();
    		toProperFirstName = firstnameField.getText().substring(0, 1).toUpperCase() + firstnameField.getText().substring(1).toLowerCase().trim();
    		firstNameValidation.setText("First name is valid.");
    		firstnameField.setText(toProperFirstName);
    		firstNameValid = true;
    	}
    	if (lastnameField.getText().isEmpty()) {
    		lastNameValidation.setText("Please enter your last name.");
    	}
    	else if (!lastnameField.getText().isEmpty()) {
    		String toProperLastName = lastnameField.getText();
    		toProperLastName = lastnameField.getText().substring(0, 1).toUpperCase() + lastnameField.getText().substring(1).toLowerCase().trim();
    		lastNameValidation.setText("Last name is valid.");
    		lastnameField.setText(toProperLastName);
    		lastNameValid = true;
    	}
    	if (!debitButton.isSelected() && !creditButton.isSelected()) {
    		buttonValidation.setText("Please select method.");
    	}
    	else {
    		buttonValidation.setText("");
    		buttonValid = true;
    	}
    	if (cardNumberField.getText().isEmpty()) {
    		cardNumberValidation.setText("Please enter card info.");
    	} else if (!(cardNumberField.getText().length() == CREDIT_MAX))  {
    		cardNumberValidation.setText("Please enter 16 digits.");
    	} else {
    		cardNumberValidation.setText("Card Number is valid.");
    		cardNumberValid = true;
    	}
    	if (dateField.getValue() == null) {
    		cardDateValidation.setText("Please enter date.");
    	} else {
    		cardDateValidation.setText("Date is valid.");
    		cardDateValid = true;
    	}
    	if (cvvtextField.getText().isEmpty()) {
    		cvvValidation.setText("Please enter CVV digits.");  
    	} else if (!(cvvtextField.getText().length() == CVV_MAX)) {
    		cvvValidation.setText("Please enter 3 digits.");
    	} else {
    		cvvValidation.setText("CVV Code is valid.");
    		cvvCodeValid = true;
    	}
    	if (passwordField.getText().isEmpty()){
    		passwordValidation.setText("Please enter password.");
    		
    	} else if (!(passwordField.getText().matches(PASSWORD_PATTERN))) {
    		passwordValidation.setText("Password is not valid. Try again.");
    		
    	} else if (passwordField.getText().matches(PASSWORD_PATTERN)) {
    		passwordValidation.setText("Password is valid.");
    		passwordValid = true;
    	}
    	
    	if (!(confirmPasswordField.getText().matches(passwordField.getText()))) {
    		confirmPasswordValidation.setText("Please confirm your password.");
    	} else if (confirmPasswordField.getText().isEmpty()) {
    		confirmPasswordValidation.setText("Confirmation not valid.");
    		
    	} else if (confirmPasswordField.getText().matches(passwordField.getText())) {
    		confirmPasswordValidation.setText("Password Confirmed.");
    		confirmPasswordValid = true;
    	}
    	
    	if (emailTextField.getText().isEmpty()) {
    		emailValidation.setText("Please enter email.");
    	} else if (!(emailTextField.getText().matches(EMAIL_PATTERN))) {
    		emailValidation.setText("Please enter valid email.");
    		
    	} else if(emailTextField.getText().matches(EMAIL_PATTERN)) {
    		emailValidation.setText("Email is valid.");
    		emailValid = true;
    	}
    	
		if (firstNameValid == true && lastNameValid == true && buttonValid == true && cardNumberValid == true && cardDateValid == true && cvvCodeValid == true && passwordValid == 		true && passwordValid == true && 		confirmPasswordValid == true && emailValid == true) {
			confirmButton.setVisible(true);

		}
    	//For testing purposes: Displays all current information to Console
		firstName = firstnameField.getText();
    	lastName = lastnameField.getText();
    	movieID = paymentID;
    	cardNumber = cardNumberField.getText();
    	cardDate = dateField.getValue();
    	cvvCode = cvvtextField.getText();
    	getPassword = passwordField.getText();
    	getConfirm = confirmPasswordField.getText();
    	getEmail = emailTextField.getText();
    	
    	System.out.println("First Name: \t" + firstName);
    	System.out.println("last Name: \t" + lastName);
    	System.out.println("Payment Method ID: \t" + movieID);
    	System.out.println("Card Number: \t" + cardNumber);
    	System.out.println("Card Expiration Date: \t" + cardDate);
    	System.out.println("CVV Code: \t" + cvvCode);
    	System.out.println("Password: \t" + getPassword);
    	System.out.println("Confirmation: \t" + getConfirm);
    	System.out.println("Email: \t" + getEmail);
    }
    @FXML
    void confirmSubmission(ActionEvent event) throws ClassNotFoundException, SQLException {//confirms all input
    	Connection con;
    	Class.forName("oracle.jdbc.driver.OracleDriver");               
		 
		 // getting connection               
		 con = DriverManager.getConnection(  "jdbc:oracle:thin:@oracle.gulfcoast.edu:1539:CLASS",             
				 							"Java6596", "Java296");              
		 // Test connection is null or not		 
		 if (con != null)                    
			 System.out.println("Connection established successfully!");
		 else                     
			 System.out.println("No Connection!");  
		 
		 Statement statement = con.createStatement();
		 
		 String getlastID = "Select MEMBER_ID FROM mm_member ORDER BY ROWNUM DESC FETCH FIRST 1 ROW ONLY";
		 ResultSet getID = statement.executeQuery(getlastID);
		 while (getID.next()) {
		 String newID = getID.getString("MEMBER_ID");
		 finalID = Integer.parseInt(newID) + 1;
		 }

		 String insertNewMember = "INSERT INTO MM_MEMBER (MEMBER_ID, LAST, FIRST, CREDIT_CARD, PASSWORD, EMAIL_ADDRESS, CVV_CODE, CC_EXP_DATE)" +
		 "VALUES ('"+finalID+"', '"+firstName+"', '"+lastName+"', '"+cardNumber+"', '"+getPassword+"', '"+getEmail+"', '"+cvvCode+"', TO_DATE('"+cardDate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS'))";
    	
		 ResultSet registerUser = statement.executeQuery(insertNewMember);
		 Stage stage = (Stage) confirmButton.getScene().getWindow();
		 stage.close();
    }
    @FXML
    void quitProgram(ActionEvent event) { //quits the program
    	Platform.exit();
    	
    	
   }
    
    
    
}





