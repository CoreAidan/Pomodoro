package com.coreaidan.pomodoro.controllers;

import com.coreaidan.pomodoro.model.AttemptKind;
import com.coreaidan.pomodoro.model.Attempt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Home {
    @FXML private VBox container;
    @FXML private Label title;

    private Attempt currentAttemtp;

    private void prepareAttempt(AttemptKind kind){
        clearAttemptStyles();
        currentAttemtp = new Attempt(kind, "");
        addAttemptStyle(kind);
        title.setText(kind.getDisplayName());
    }

    private void addAttemptStyle(AttemptKind kind) {
        container.getStyleClass().add(kind.toString().toLowerCase());
    }

    private void clearAttemptStyles(){
        for(AttemptKind kind: AttemptKind.values()){
            container.getStyleClass().remove(kind.toString().toLowerCase());
        }
    }


    public void DEBUG(ActionEvent actionEvent) {
        System.out.println("hi mom");
    }
}
