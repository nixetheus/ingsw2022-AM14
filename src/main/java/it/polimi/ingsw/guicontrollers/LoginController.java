package it.polimi.ingsw.guicontrollers;

import it.polimi.ingsw.view.GuiParser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable {

  private GuiParser parser;
  public TextField username;
  public TextField hostname;
  public TextField portNumber;
  public CheckBox expertMode;
  public ChoiceBox numberOfPlayers;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    //textArea.setVisible(false);
  }

  public void setParser(GuiParser parser) {
    this.parser = parser;
  }

  @FXML
  protected void sendLoginMessage(MouseEvent event) {
    String usernameString = username.getText();
    String hostnameString = hostname.getText();
    String portNumberString = portNumber.getText();
    parser.sendLoginMessage(usernameString);
  }

  @FXML
  protected void sendLoginParams(MouseEvent event) {
    Boolean isGameExpert = expertMode.isSelected();
    String numberOfPlayersString = "";
    parser.sendLoginParameters(isGameExpert, "4 Players");
  }

}
