package com.damiskot.json;

import javax.sound.midi.ShortMessage;

public class JsonThread {

    private String keyToClick;
    private ShortMessage[] messagesOn;
    private ShortMessage[] messagesOff;

    private boolean ccThread;
    private boolean velocityThread;

    private int howManyNotes;
    private boolean backMessage;

    public JsonThread(String keyToClick, ShortMessage[] messagesOn, ShortMessage[] messagesOff, boolean ccThread, boolean velocityThread, int howManyNotes, boolean backMessage) {
        this.keyToClick = keyToClick;
        this.messagesOn = messagesOn;
        this.messagesOff = messagesOff;
        this.ccThread = ccThread;
        this.velocityThread = velocityThread;
        this.howManyNotes = howManyNotes;
        this.backMessage = backMessage;
    }

    public String getKeyToClick() {
        return keyToClick;
    }

    public ShortMessage[] getMessagesOn() {
        return messagesOn;
    }

    public ShortMessage[] getMessagesOff() {
        return messagesOff;
    }

    public boolean isCcThread() {
        return ccThread;
    }

    public boolean isVelocityThread() {
        return velocityThread;
    }

    public int getHowManyNotes() {
        return howManyNotes;
    }

    public boolean isBackMessage() {
        return backMessage;
    }
}
