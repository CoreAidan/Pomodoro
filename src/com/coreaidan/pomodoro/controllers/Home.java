package com.coreaidan.pomodoro.controllers;

import com.coreaidan.pomodoro.model.AttemptKind;
import com.coreaidan.pomodoro.model.Attempt;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Home {
    @FXML private VBox container;
    @FXML private Label title;

    private Attempt currentAttemtp;

    private StringProperty timerText;

    public Home (){
        timerText = new SimpleStringProperty();
        setTimerText(3000);
    }

    public String getTimerText() {
        return timerText.get();
    }

    public StringProperty timerTextProperty() {
        return timerText;
    }

    public void setTimerText(String timerText) {
        this.timerText.set(timerText);
    }

    public void setTimerText(int remainingSeconds){
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        setTimerText(String.format("%02d:%02d", minutes, seconds));
    }

    private void prepareAttempt(AttemptKind kind){
        clearAttemptStyles();
        currentAttemtp = new Attempt(kind, "");
        addAttemptStyle(kind);
        title.setText(kind.getDisplayName());
        setTimerText(currentAttemtp.getRemainingSeconds());
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
