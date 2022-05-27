package it.polimi.ingsw.guicontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CloudController implements Initializable {

  public Circle student0;
  public Circle student1;
  public Circle student2;
  public Circle student3;
  Vector<Circle> students;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    students = new Vector<>();
    students.add(student0);
    students.add(student1);
    students.add(student2);
    students.add(student3);
    hideStudents();
  }

  @FXML
  public void onClickCloudTile(MouseEvent event) {
    System.out.println(((StackPane)event.getSource()).getId());
  }

  public void hideStudents() {
    for (Circle student : students) {
      student.setDisable(true);
      student.setVisible(false);
    }
  }

  public void setStudents(int[] colors) {
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
        }
        studVisibleIndex++;
      }
    }

    for (; studVisibleIndex < students.size(); studVisibleIndex++) {
      students.elementAt(studVisibleIndex).setDisable(true);
      students.elementAt(studVisibleIndex++).setVisible(false);
    }
  }
}
