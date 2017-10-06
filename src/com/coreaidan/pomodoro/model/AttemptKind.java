package com.coreaidan.pomodoro.model;

public enum AttemptKind {
    FOCUS(25 * 60, "FOCUS time"),
    BREAK(5 * 60, "BREAK time");

    private int totalSeconds;
    private String displayName;


    AttemptKind(int totalSeconds, String displayName) {
        this.totalSeconds = totalSeconds;
        this.displayName = displayName;
    }

    public int getTotalSeconds(){
        return totalSeconds;
    }

    public String getDisplayName() {
        return displayName;
    }
}
