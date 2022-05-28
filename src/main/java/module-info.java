module it.polimi.ingsw {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.kordamp.bootstrapfx.core;
  requires com.google.gson;
  requires org.jetbrains.annotations;

  exports it.polimi.ingsw.view;
  exports it.polimi.ingsw.guicontrollers;
  opens it.polimi.ingsw.guicontrollers to javafx.fxml;
  opens it.polimi.ingsw.view to javafx.fxml;

  opens it.polimi.ingsw.messages to com.google.gson;
  exports it.polimi.ingsw.messages to com.google.gson;
  opens it.polimi.ingsw.helpers to com.google.gson;
  exports it.polimi.ingsw.helpers to com.google.gson;
}