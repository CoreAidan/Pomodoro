package com.coreaidan.pomodoro.controllers;

import com.coreaidan.pomodoro.model.AttemptKind;
import com.coreaidan.pomodoro.model.Attempt;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Home {
    @FXML private VBox container;
    @FXML private Label title;

    private Attempt currentAttemtp;

    private StringProperty timerText;

    private Timeline timeLine;


    public Home (){
        timerText = new SimpleStringProperty();
        setTimerText(3000);
    }

    private void prepareAttempt(AttemptKind kind){
        reset();

        currentAttemtp = new Attempt(kind, "");
        addAttemptStyle(kind);
        title.setText(kind.getDisplayName());
        setTimerText(currentAttemtp.getRemainingSeconds());
        timeLine = new Timeline();
        timeLine.setCycleCount(kind.getTotalSeconds());
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            currentAttemtp.tick();
            setTimerText(currentAttemtp.getRemainingSeconds());
        }));
    }

    private void reset() {
        clearAttemptStyles();
        if(timeLine != null && timeLine.getStatus() == Animation.Status.RUNNING){
            timeLine.stop();
        }
    }

    public void playTimer(){
        timeLine.play();
    }

    public void pauseTimer(){
        timeLine.pause();
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

    public void handleRestart(ActionEvent actionEvent) {
        prepareAttempt(AttemptKind.FOCUS);
        playTimer();
    }
}
