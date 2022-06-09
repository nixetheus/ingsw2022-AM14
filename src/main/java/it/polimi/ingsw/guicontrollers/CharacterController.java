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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CharacterController implements Initializable {

  public Circle student0;
  public Circle student1;
  public Circle student2;
  public Circle student3;
  public Circle student4;
  public Circle student5;
  Vector<Circle> students;

  public Pane cardPane;

  public Button playCharBtn;

  public Label cost;

  public ImageView characterPic;

  public String studentCharacter0Id;
  public String studentCharacter1Id;
  public String studentCharacter2Id;

  public GameController mainController;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    students = new Vector<>();
    students.add(student0);
    students.add(student1);
    students.add(student2);
    students.add(student3);
    students.add(student4);
    students.add(student5);
  }

  @FXML
  protected void onClickStudentCharacter(MouseEvent event) {

    Circle student = ((Circle) event.getSource());

    if (Objects.equals(student.getId(), studentCharacter0Id) ||
        Objects.equals(student.getId(), studentCharacter1Id) ||
        Objects.equals(student.getId(), studentCharacter2Id))
      unClickStudentsCharacter(student);

    else {
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
    Circle student = ((Circle)event.getSource());
  }

  @FXML
  protected void onExitHoverStudentCharacter(MouseEvent event) {
    Circle student = ((Circle)event.getSource());
  }

  public void unClickStudentsCharacter(Circle clickedStudent) {
    clickedStudent.setScaleX(1); clickedStudent.setScaleY(1);
    Glow glow = new Glow(); glow.setLevel(0);
    clickedStudent.setEffect(glow);

    if (Objects.equals(clickedStudent.getId(), studentCharacter0Id))
      studentCharacter0Id = null;
    if (Objects.equals(clickedStudent.getId(), studentCharacter1Id))
      studentCharacter1Id = null;
    if (Objects.equals(clickedStudent.getId(), studentCharacter2Id))
      studentCharacter2Id = null;
  }

  @FXML
  protected void playCharacter(MouseEvent event) {
    mainController.playCharacter();
  }

  public void setStudents(int[] colors){
    int studVisibleIndex = 0;
    for (int index = 0; index < colors.length; index++) {
      for (int nOfColor = 0; nOfColor < colors[index]; nOfColor++) {
        students.elementAt(studVisibleIndex).setVisible(true);
        students.elementAt(studVisibleIndex).setDisable(false);
        switch (index) {
          case 0:  // RED
            students.elementAt(studVisibleIndex).setFill(Color.rgb(217, 77, 89));
            break;
          case 1:  // GREEN
            students.elementAt(studVisibleIndex).setFill(Color.rgb(109, 166, 97));
            break;
          case 2:  // YELLOW
            students.elementAt(studVisibleIndex).setFill(Color.rgb(254, 207, 53));
            break;
          case 3:  // PINK
            students.elementAt(studVisibleIndex).setFill(Color.rgb(239, 132, 180));
            break;
          case 4:  // BLUE
            students.elementAt(studVisibleIndex).setFill(Color.rgb(33, 187, 239));
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + index);
        }
        studVisibleIndex++;
      }
    }

    for (;studVisibleIndex < students.size(); studVisibleIndex++) {
      students.elementAt(studVisibleIndex).setDisable(true);
      students.elementAt(studVisibleIndex).setVisible(false);
    }
  }

  public void changeCharacterPic(String pathToFile) {
    characterPic.setImage(new Image(pathToFile));
  }

  public void setCost(int coins) {
    cost.setText(Integer.toString(coins));
  }
}
