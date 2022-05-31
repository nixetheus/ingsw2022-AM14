package it.polimi.ingsw.guicontrollers;

import it.polimi.ingsw.view.GuiParser;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameController implements Initializable {

  public GridPane scene;
  private GuiParser parser;

  public GridPane textArea;
  public Label textAreaText;
  private Vector<String> textAreaStrings;

  // Cloud Tiles
  public StackPane cloud0;
  public StackPane cloud1;
  public StackPane cloud2;
  public StackPane cloud3;
  public CloudController cloud0Controller;
  public CloudController cloud1Controller;
  public CloudController cloud2Controller;
  public CloudController cloud3Controller;
  public Vector<CloudController> cloudControllers;


  // Assistants
  public Pane assistant0;
  public Pane assistant1;
  public Pane assistant2;
  public Pane assistant3;
  public Pane assistant4;
  public Pane assistant5;
  public Pane assistant6;
  public Pane assistant7;
  public Pane assistant8;
  public Pane assistant9;

  // Characters
  public Pane character0;
  public Pane character1;
  public Pane character2;
  public CharacterController character0Controller;
  public CharacterController character1Controller;
  public CharacterController character2Controller;

  // Islands
  public StackPane island0;
  public StackPane island1;
  public StackPane island2;
  public StackPane island3;
  public StackPane island4;
  public StackPane island5;
  public StackPane island6;
  public StackPane island7;
  public StackPane island8;
  public StackPane island9;
  public StackPane island10;
  public StackPane island11;
  public Vector<StackPane> islands;
  // Controllers
  public IslandController island0Controller;
  public IslandController island1Controller;
  public IslandController island2Controller;
  public IslandController island3Controller;
  public IslandController island4Controller;
  public IslandController island5Controller;
  public IslandController island6Controller;
  public IslandController island7Controller;
  public IslandController island8Controller;
  public IslandController island9Controller;
  public IslandController island10Controller;
  public IslandController island11Controller;
  public Vector<IslandController> islandsControllers;

  // Other Players boards
  int numberOfPlayers = 2;
  int currentBoardIndex = 0;
  @FXML private Parent player0Board;
  @FXML private Parent player1Board;
  @FXML private Parent player2Board;
  @FXML private Parent player3Board;  // PlayerBoard
  @FXML private PlayerBoardController player0BoardController;
  @FXML private PlayerBoardController player1BoardController;
  @FXML private PlayerBoardController player2BoardController;
  @FXML private PlayerBoardController player3BoardController;
  Vector<Parent> boards = new Vector<>();
  public Vector<PlayerBoardController> BoardsControllers = new Vector<>();

  // Coins
  public Label playerCoins;

  // Active character
  private boolean isCharacterActive;
  private String activeCharacterId;
  private Pane activeCharacterPane;

  public int playerId = 0;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    boards.add(player0Board);
    boards.add(player1Board);
    boards.add(player2Board);
    boards.add(player3Board);

    BoardsControllers.add(player0BoardController);
    BoardsControllers.add(player1BoardController);
    BoardsControllers.add(player2BoardController);
    BoardsControllers.add(player3BoardController);

    for(Parent board : boards) {
      board.setDisable(true);
      board.setVisible(false);
    }

    textAreaStrings = new Vector<>();

    islands = new Vector<>();
    islands.add(island0);
    islands.add(island1);
    islands.add(island2);
    islands.add(island3);
    islands.add(island4);
    islands.add(island5);
    islands.add(island6);
    islands.add(island7);
    islands.add(island8);
    islands.add(island9);
    islands.add(island10);
    islands.add(island11);

    islandsControllers = new Vector<>();
    islandsControllers.add(island0Controller);
    islandsControllers.add(island1Controller);
    islandsControllers.add(island2Controller);
    islandsControllers.add(island3Controller);
    islandsControllers.add(island4Controller);
    islandsControllers.add(island5Controller);
    islandsControllers.add(island6Controller);
    islandsControllers.add(island7Controller);
    islandsControllers.add(island8Controller);
    islandsControllers.add(island9Controller);
    islandsControllers.add(island10Controller);
    islandsControllers.add(island11Controller);

    cloudControllers = new Vector<>();
    cloudControllers.add(cloud0Controller);
    cloudControllers.add(cloud1Controller);
    cloudControllers.add(cloud2Controller);
    cloudControllers.add(cloud3Controller);
  }

  public void setGuiParser(GuiParser parser) {
    this.parser = parser;
    for (PlayerBoardController boardController : BoardsControllers)
      boardController.setParser(parser);
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
    boards.elementAt(playerId).setDisable(false);
    boards.elementAt(playerId).setVisible(true);
    currentBoardIndex = playerId;
    if (numberOfPlayers == 3) {
      BoardsControllers.elementAt(playerId).setTowersColor(playerId % 2);
    } else {
      BoardsControllers.elementAt(playerId).setTowersColor(playerId % 2);
    }
    gameSetup(numberOfPlayers);
  }

  @FXML
  protected void onChangeBoardUp(MouseEvent event) {
    currentBoardIndex++;
    currentBoardIndex %= numberOfPlayers;

    for(Parent board : boards) {
      board.setDisable(true);
      board.setVisible(false);
    }

    if (currentBoardIndex == playerId)
      boards.elementAt(currentBoardIndex).setDisable(false);
    boards.elementAt(currentBoardIndex).setVisible(true);
  }

  @FXML
  protected void onChangeBoardDown(MouseEvent event) {
    currentBoardIndex--;
    currentBoardIndex = (currentBoardIndex < 0)? numberOfPlayers - 1 : currentBoardIndex;

    for(Parent board : boards) {
      board.setDisable(true);
      board.setVisible(false);
    }

    if (currentBoardIndex == playerId)
      boards.elementAt(currentBoardIndex).setDisable(false);
    boards.elementAt(currentBoardIndex).setVisible(true);
  }

  @FXML
  protected void onClickScene(MouseEvent event) {

    if (textAreaStrings.size() == 1) {
      textAreaStrings.remove(0);
      textArea.setVisible(false);
    } else if (textAreaStrings.size() > 1) {
      textAreaStrings.remove(0);
      textAreaText.setText(textAreaStrings.elementAt(0));
      textArea.setVisible(true);
    }

    if (event.getX() < 950 || event.getY() < 500 || event.getY() > 600) {
      BoardsControllers.elementAt(playerId).unClickStudentsEntrance();
    }

    Bounds boundsCh0 = character0.localToScene(character0.getBoundsInLocal());
    boolean char0NotClicked = event.getX() < boundsCh0.getMinX() || event.getX() > boundsCh0.getMaxX() ||
        event.getY() < boundsCh0.getMinY() || event.getY() > boundsCh0.getMaxY();
    if (char0NotClicked) {
      character0Controller.unClickStudentsCharacter();
    }

    Bounds boundsCh1 = character1.localToScene(character1.getBoundsInLocal());
    boolean char1NotClicked = event.getX() < boundsCh1.getMinX() || event.getX() > boundsCh1.getMaxX() ||
        event.getY() < boundsCh1.getMinY() || event.getY() > boundsCh1.getMaxY();
    if (char1NotClicked) {
      character1Controller.unClickStudentsCharacter();
    }

    Bounds boundsCh2 = character2.localToScene(character2.getBoundsInLocal());
    boolean char2NotClicked = event.getX() < boundsCh2.getMinX() || event.getX() > boundsCh2.getMaxX() ||
        event.getY() < boundsCh2.getMinY() || event.getY() > boundsCh2.getMaxY();
    if (char2NotClicked) {
      character2Controller.unClickStudentsCharacter();
    }

    if (char0NotClicked && char1NotClicked && char2NotClicked) {
      characterWasUsed();
    }
  }

  @FXML
  protected void onClickTextAreaButton(MouseEvent event) {
    if (textAreaStrings.size() <= 1) {
      textAreaStrings.remove(0);
      textArea.setVisible(false);
    } else {
      textAreaStrings.remove(0);
      textAreaText.setText(textAreaStrings.elementAt(0));
      textArea.setVisible(true);
    }
  }

  @FXML
  protected void onClickIsland(MouseEvent event) {
    StackPane island = (StackPane)event.getSource();
    String studentEntrance = BoardsControllers.elementAt(playerId).studentEntranceId;

    // CHARACTER CARD
    if (isCharacterActive) {
      // TODO
    }
    // MOVE STUDENT ENTRANCE TO ISLAND
    else if (studentEntrance != null) {
      parser.moveStudentFromEntrance(island.getId(), false, studentEntrance);
      BoardsControllers.elementAt(playerId).unClickStudentsEntrance();
    }
    // MOVE MOTHER NATURE
    else {
      parser.moveMotherNature(island.getId());
    }

  }

  @FXML
  protected void onClickCloudTile(MouseEvent event) {
    StackPane cloudTile = (StackPane)event.getSource();
    parser.moveStudentsFromCloud(cloudTile.getId());
  }

  @FXML
  protected void onEnterHoverCard(MouseEvent event) {
    Pane card = (Pane)event.getSource();
    if (!Objects.equals(card.getId(), activeCharacterId)) {
      card.setTranslateY(card.getTranslateY() - 20);
      card.setScaleX(1.2);
      card.setScaleY(1.2);
    }
  }

  @FXML
  protected void onExitHoverCard(MouseEvent event) {
    Pane card = (Pane)event.getSource();
    if (!Objects.equals(card.getId(), activeCharacterId)) {
      card.setTranslateY(card.getTranslateY() + 20);
      card.setScaleX(1);
      card.setScaleY(1);
    }
  }

  @FXML
  protected void onClickAssistant(MouseEvent event) {
    Pane assistant = (Pane)event.getSource();
    parser.playAssistant(assistant.getId());
  }

  @FXML
  protected void onClickCharacter(MouseEvent event) {
    Pane character = (Pane)event.getSource();
    if (event.getY() < character0Controller.cardPane.getHeight()) {

      if (character != activeCharacterPane)
        characterWasUsed();

      System.out.println(character.getId());
      isCharacterActive = true;
      activeCharacterId = character.getId();
      activeCharacterPane = character;
      Glow glow = new Glow(); glow.setLevel(0.5);
      character.setEffect(glow);
      character.setScaleX(1.2);
      character.setScaleY(1.2);
      parser.playCharacter(character.getId());
    }
  }

  public void characterWasUsed() {
    if (activeCharacterPane != null) {
      isCharacterActive = false;
      activeCharacterId = null;
      activeCharacterPane.setTranslateY(activeCharacterPane.getTranslateY() + 20);
      activeCharacterPane.setScaleX(1);
      activeCharacterPane.setScaleY(1);
      Glow glow = new Glow();
      glow.setLevel(0);
      activeCharacterPane.setEffect(glow);
      activeCharacterPane = null;
    }
  }

  public void gameSetup(int numberOfPlayers) {

    // Cloud Tiles
    if (numberOfPlayers == 2) {
      cloud2.setVisible(false);
      cloud3.setVisible(false);
      cloud2Controller.hideStudents();
      cloud3Controller.hideStudents();
    } else if (numberOfPlayers == 3) {
      cloud3.setVisible(false);
      cloud3Controller.hideStudents();
    }
  }

  public void hideAssistant(int numberOfAssistant) {
    switch (numberOfAssistant) {
      case 0: {
        assistant0.setDisable(true);
        assistant0.setVisible(false);
      }
      break;
      case 1: {
        assistant1.setDisable(true);
        assistant1.setVisible(false);
      }
      break;
      case 2: {
        assistant2.setDisable(true);
        assistant2.setVisible(false);
      }
      break;
      case 3: {
        assistant3.setDisable(true);
        assistant3.setVisible(false);
      }
      break;
      case 4: {
        assistant4.setDisable(true);
        assistant4.setVisible(false);
      }
      break;
      case 5: {
        assistant5.setDisable(true);
        assistant5.setVisible(false);
      }
      break;
      case 6: {
        assistant6.setDisable(true);
        assistant6.setVisible(false);
      }
      break;
      case 7: {
        assistant7.setDisable(true);
        assistant7.setVisible(false);
      }
      break;
      case 8: {
        assistant8.setDisable(true);
        assistant8.setVisible(false);
      }
      break;
      case 9: {
        assistant9.setDisable(true);
        assistant9.setVisible(false);
      }
      break;
    }
  }

  public void setTextArea(String text) {
    textAreaStrings.add(text);
    textAreaText.setText(textAreaStrings.elementAt(0));
    textArea.setVisible(true);
  }

  public void deleteIsland(int islandIndex) {
    islands.elementAt(islandIndex).setDisable(true);
    islands.elementAt(islandIndex).setVisible(false);
  }

  public void setPlayerCoins(int numberOfCoins) {
    playerCoins.setText("Coins: " + numberOfCoins);
  }
}
