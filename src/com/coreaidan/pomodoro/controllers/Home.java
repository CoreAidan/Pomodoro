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
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;


public class Home {
    @FXML private VBox container;
    @FXML private Label title;
    @FXML private TextArea message;

    private final AudioClip applause;

    private Attempt currentAttemtp;

    private StringProperty timerText;

    private Timeline timeLine;


    public Home (){
        timerText = new SimpleStringProperty();
        setTimerText(0);

        applause= new AudioClip(getClass().getResource("/sounds/applause6.mp3").toExternalForm());
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

        timeLine.setOnFinished(event -> {
            saveCurrentAttempt();
            applause.play();
            prepareAttempt(currentAttemtp.getKind() == AttemptKind.FOCUS ?
                    AttemptKind.BREAK : AttemptKind.FOCUS);
        });
    }

    private void saveCurrentAttempt() {
        currentAttemtp.setMessage(message.getText());
        currentAttemtp.save();
    }

    private void reset() {
        clearAttemptStyles();
        if(timeLine != null && timeLine.getStatus() == Animation.Status.RUNNING){
            timeLine.stop();
        }
    }

    public void playTimer(){
        container.getStyleClass().add("playing");
        timeLine.play();
    }

    public void pauseTimer(){
        container.getStyleClass().remove("playing");
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
        container.getStyleClass().remove("playing");
        for(AttemptKind kind: AttemptKind.values()){
            container.getStyleClass().remove(kind.toString().toLowerCase());
        }
    }

    public void handleRestart(ActionEvent actionEvent) {
        prepareAttempt(AttemptKind.FOCUS);
        playTimer();
    }

    public void handlePlay(ActionEvent actionEvent) {
        if(currentAttemtp == null){
            handleRestart(actionEvent);
        }else{
            playTimer();
        }

    }

    public void handlePause(ActionEvent actionEvent) {
        pauseTimer();
    }
}
