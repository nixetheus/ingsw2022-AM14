package it.polimi.ingsw.guicontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

  public Label cost;

  public ImageView characterPic;

  String studentCharacterId;

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
    unClickStudentsCharacter();
    Circle student = ((Circle)event.getSource());
    studentCharacterId = student.getId();

    Glow glow = new Glow(); glow.setLevel(1);
    student.setEffect(glow);
    student.setScaleX(1.2); student.setScaleY(1.2);
  }

  @FXML
  protected void onHoverStudentCharacter(MouseEvent event) {
    Circle student = ((Circle)event.getSource());
  }

  @FXML
  protected void onExitHoverStudentCharacter(MouseEvent event) {
    Circle student = ((Circle)event.getSource());
  }

  public void unClickStudentsCharacter() {
    for(Circle student : students) {
      student.setScaleX(1); student.setScaleY(1);
      Glow glow = new Glow(); glow.setLevel(0);
      student.setEffect(glow);
    }
    studentCharacterId = null;
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
