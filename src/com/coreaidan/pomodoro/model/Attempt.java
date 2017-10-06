package com.coreaidan.pomodoro.model;

public class Attempt {
    private String message;
    private int remainingSeconds;
    private AttemptKind kind;


    public Attempt(AttemptKind kind, String message) {
       this.message = message;
        this.kind = kind;
        this.remainingSeconds = kind.getTotalSeconds();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public AttemptKind getKind() {
        return kind;
    }
}
