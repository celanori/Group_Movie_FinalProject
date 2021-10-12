/** Checkout.java and Checkout.FXML made by Nicholas Que. Group Movie 2020. Modified 11/18 **/
package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class Checkout {

    @FXML
    private TitledPane registrationTextTitle;

    @FXML
    private AnchorPane clearButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label ScreenLabel;

    @FXML
    private Label CheckoutLabel;

    @FXML
    private Label cartLabel;

    @FXML
    private ListView<?> CartList;

    @FXML
    private RadioButton AgreeButton;

    @FXML
    private Label totalLabel;

}
