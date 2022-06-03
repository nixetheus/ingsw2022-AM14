package it.polimi.ingsw.guicontrollers;

import it.polimi.ingsw.view.GuiParser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    if (usernameString != null)
      parser.sendLoginMessage(usernameString);
  }

  @FXML
  protected void sendLoginParams(MouseEvent event) {
    boolean isGameExpert = expertMode.isSelected();
    String numberOfPlayersString = (String) numberOfPlayers.getSelectionModel().getSelectedItem();
    parser.sendLoginParameters(isGameExpert, numberOfPlayersString);
  }

  @FXML
  protected void onPressEnterLogin(KeyEvent key) {
    if(key.getCode() == KeyCode.ENTER) {
      String usernameString = username.getText();
      if (usernameString != null)
        parser.sendLoginMessage(usernameString);
    }
  }

  @FXML
  protected void onPressEnterParams(KeyEvent key) {
    if(key.getCode() == KeyCode.ENTER) {
      boolean isGameExpert = expertMode.isSelected();
      String numberOfPlayersString = "";
      parser.sendLoginParameters(isGameExpert, "4 Players");
    }
  }

}
