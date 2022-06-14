package it.polimi.ingsw.guicontrollers;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CharacterController implements Initializable {

  public Circle student0;
  public Circle student1;
  public Circle student2;
  public Circle student3;
  public Circle student4;
  public Circle student5;

  public Pane cardPane;
  public Button playCharBtn;
  public Label cost;

  public ImageView characterPic0;
  public ImageView characterPic1;
  public ImageView characterPic2;
  public ImageView characterPic3;
  public ImageView characterPic4;
  public ImageView characterPic5;
  public ImageView characterPic6;
  public ImageView characterPic7;
  public ImageView characterPic8;
  public ImageView characterPic9;
  public ImageView characterPic10;
  public ImageView characterPic11;

  public String studentCharacter0Id;
  public String studentCharacter1Id;
  public String studentCharacter2Id;

  public GameController mainController;
  Vector<Circle> students;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    students = new Vector<>();
    students.add(student0);
    students.add(student1);
    students.add(student2);
    students.add(student3);
    students.add(student4);
    students.add(student5);

    characterPic0.setVisible(false);
    characterPic1.setVisible(false);
    characterPic2.setVisible(false);
    characterPic3.setVisible(false);
    characterPic4.setVisible(false);
    characterPic5.setVisible(false);
    characterPic6.setVisible(false);
    characterPic7.setVisible(false);
    characterPic8.setVisible(false);
    characterPic9.setVisible(false);
    characterPic10.setVisible(false);
    characterPic11.setVisible(false);
  }

  @FXML
  protected void onClickStudentCharacter(MouseEvent event) {

    Circle student = ((Circle) event.getSource());

    if (Objects.equals(student.getId(), studentCharacter0Id) ||
        Objects.equals(student.getId(), studentCharacter1Id) ||
        Objects.equals(student.getId(), studentCharacter2Id)) {
      unClickStudentsCharacter(student);
    } else {
      if (studentCharacter0Id == null) {
        studentCharacter0Id = student.getId();
        Glow glow = new Glow();
        glow.setLevel(1);
        student.setEffect(glow);
        student.setScaleX(1.2);
        student.setScaleY(1.2);
      } else if (studentCharacter1Id == null) {
        studentCharacter1Id = student.getId();
        Glow glow = new Glow();
        glow.setLevel(1);
        student.setEffect(glow);
        student.setScaleX(1.2);
        student.setScaleY(1.2);
      } else if (studentCharacter2Id == null) {
        studentCharacter2Id = student.getId();
        Glow glow = new Glow();
        glow.setLevel(1);
        student.setEffect(glow);
        student.setScaleX(1.2);
        student.setScaleY(1.2);
      }
    }
  }

  @FXML
  protected void onHoverStudentCharacter(MouseEvent event) {
    Circle student = ((Circle) event.getSource());
  }

  @FXML
  protected void onExitHoverStudentCharacter(MouseEvent event) {
    Circle student = ((Circle) event.getSource());
  }

  public void unClickStudentsCharacter(Circle clickedStudent) {
    clickedStudent.setScaleX(1);
    clickedStudent.setScaleY(1);
    Glow glow = new Glow();
    glow.setLevel(0);
    clickedStudent.setEffect(glow);

    if (Objects.equals(clickedStudent.getId(), studentCharacter0Id)) {
      studentCharacter0Id = null;
    }
    if (Objects.equals(clickedStudent.getId(), studentCharacter1Id)) {
      studentCharacter1Id = null;
    }
    if (Objects.equals(clickedStudent.getId(), studentCharacter2Id)) {
      studentCharacter2Id = null;
    }
  }

  @FXML
  protected void playCharacter(MouseEvent event) {
    mainController.playCharacter();
  }

  public void setStudents(int[] colors) {
    int studVisibleIndex = 0;
    for (int index = 0; index < colors.length; index++) {
      for (int nOfColor = 0; nOfColor < colors[index]; nOfColor++) {
        students.elementAt(studVisibleIndex).setVisible(true);
        students.elementAt(studVisibleIndex).setDisable(false);
        switch (index) {
          case 3:  // RED
            students.elementAt(studVisibleIndex).setFill(Color.rgb(217, 77, 89));
            break;
          case 2:  // GREEN
            students.elementAt(studVisibleIndex).setFill(Color.rgb(109, 166, 97));
            break;
          case 0:  // YELLOW
            students.elementAt(studVisibleIndex).setFill(Color.rgb(254, 207, 53));
            break;
          case 4:  // PINK
            students.elementAt(studVisibleIndex).setFill(Color.rgb(239, 132, 180));
            break;
          case 1:  // BLUE
            students.elementAt(studVisibleIndex).setFill(Color.rgb(33, 187, 239));
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + index);
        }
        studVisibleIndex++;
      }
    }

    for (; studVisibleIndex < students.size(); studVisibleIndex++) {
      students.elementAt(studVisibleIndex).setDisable(true);
      students.elementAt(studVisibleIndex).setVisible(false);
    }
  }

  public void setCharacter(int characterId) {
    switch (characterId) {
      case 0:
        characterPic0.setVisible(true);
        break;
      case 1:
        characterPic1.setVisible(true);
        break;
      case 2:
        characterPic2.setVisible(true);
        break;
      case 3:
        characterPic3.setVisible(true);
        break;
      case 4:
        characterPic4.setVisible(true);
        break;
      case 5:
        characterPic5.setVisible(true);
        break;
      case 6:
        characterPic6.setVisible(true);
        break;
      case 7:
        characterPic7.setVisible(true);
        break;
      case 8:
        characterPic8.setVisible(true);
        break;
      case 9:
        characterPic9.setVisible(true);
        break;
      case 10:
        characterPic10.setVisible(true);
        break;
      case 11:
        characterPic11.setVisible(true);
        break;

    }


  }

  public void setCost(int coins) {
    cost.setText(Integer.toString(coins));
  }
}
