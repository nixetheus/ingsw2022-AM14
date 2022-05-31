package it.polimi.ingsw.guicontrollers;

import it.polimi.ingsw.view.GuiParser;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.css.converter.PaintConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PlayerBoardController implements Initializable {

  public VBox redStudents;
  public VBox blueStudents;
  public VBox pinkStudents;
  public VBox greenStudents;
  public VBox yellowStudents;

  public HBox professors;

  public GridPane diningRoom;
  
  public GridPane playerTowers;

  // Entrance
  public Circle studentEntrance0;
  public Circle studentEntrance1;
  public Circle studentEntrance2;
  public Circle studentEntrance3;
  public Circle studentEntrance4;
  public Circle studentEntrance5;
  public Circle studentEntrance6;
  public Circle studentEntrance7;
  Vector<Circle> studentsEntrance;

  // For moving
  public String studentEntranceId;
  public String studentDiningRoomId;

  private GuiParser parser;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    showStudents(0, redStudents);
    showStudents(0, blueStudents);
    showStudents(0, pinkStudents);
    showStudents(0, greenStudents);
    showStudents(0, yellowStudents);
    showProfessors(0);

    studentsEntrance = new Vector<>();
    studentsEntrance.add(studentEntrance0);
    studentsEntrance.add(studentEntrance1);
    studentsEntrance.add(studentEntrance2);
    studentsEntrance.add(studentEntrance3);
    studentsEntrance.add(studentEntrance4);
    studentsEntrance.add(studentEntrance5);
    studentsEntrance.add(studentEntrance6);
    studentsEntrance.add(studentEntrance7);
  }

  @FXML
  protected void onClickStudentEntrance(MouseEvent event) {
    unClickStudentsEntrance();
    Circle student = ((Circle)event.getSource());
    studentEntranceId = student.getId();

    Glow glow = new Glow(); glow.setLevel(1);
    student.setEffect(glow);
    student.setScaleX(1.2); student.setScaleY(1.2);
  }

  @FXML
  protected void onHoverStudentEntrance(MouseEvent event) {
    Circle student = ((Circle)event.getSource());
  }

  @FXML
  protected void onExitHoverStudentEntrance(MouseEvent event) {
    Circle student = ((Circle)event.getSource());
  }

  @FXML
  protected void onClickDiningRoom(MouseEvent event) {
    GridPane diningRoom = ((GridPane)event.getSource());
    System.out.println(diningRoom.getId());
  }

  @FXML
  protected void onClickStudentsDiningRoom(MouseEvent event) {
    VBox diningRoomStudents = ((VBox)event.getSource());
    System.out.println(diningRoomStudents.getId());
    studentDiningRoomId = diningRoomStudents.getId();
    if (studentEntranceId != null) {
      parser.moveStudentFromEntrance("", true, studentEntranceId);
      unClickStudentsEntrance();
    }
  }

  public void setParser(GuiParser parser) {
    this.parser = parser;
  }

  public void unClickStudentsEntrance() {
    for(Circle student : studentsEntrance) {
      student.setScaleX(1); student.setScaleY(1);
      Glow glow = new Glow(); glow.setLevel(0);
      student.setEffect(glow);
    }
    studentEntranceId = null;
  }

  public void showStudents(int nStudents, VBox colorBox) {
    for (int index = 0; index < nStudents; index++) {
      colorBox.getChildren().get(colorBox.getChildren().size() - (index + 1)).setVisible(true);
    }
  }

  public void showProfessors(int professorFlag) {
    for (int index = 0; index < professors.getChildren().size(); index++) {
      professors.getChildren().get(index).setVisible((professorFlag & (1 << index)) > 0);
    }
  }

  public void showTowers(int nTowers) {
    for (int index = 0; index < playerTowers.getChildren().size(); index++) {
      playerTowers.getChildren().get(index).setVisible(index < nTowers);
    }
  }

  public void setEntranceStudents(int[] studentsColors) {
    int studVisibleIndex = 0;
    for (int index = 0; index < studentsColors.length; index++) {
      for (int nOfColor = 0; nOfColor < studentsColors[index]; nOfColor++) {
        studentsEntrance.elementAt(studVisibleIndex).setVisible(true);
        switch (index) {
          case 3:  // RED
              studentsEntrance.elementAt(studVisibleIndex).setFill(Color.rgb(217, 77, 89));
            break;
          case 2:  // GREEN
              studentsEntrance.elementAt(studVisibleIndex).setFill(Color.rgb(109, 166, 97));
            break;
          case 0:  // YELLOW
              studentsEntrance.elementAt(studVisibleIndex).setFill(Color.rgb(254, 207, 53));
            break;
          case 4:  // PINK
              studentsEntrance.elementAt(studVisibleIndex).setFill(Color.rgb(239, 132, 180));
            break;
          case 1:  // BLUE
              studentsEntrance.elementAt(studVisibleIndex).setFill(Color.rgb(33, 187, 239));
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + index);
        }
        studVisibleIndex++;
      }
    }

    for (;studVisibleIndex < studentsEntrance.size(); studVisibleIndex++)
      studentsEntrance.elementAt(studVisibleIndex++).setVisible(false);
  }

}
