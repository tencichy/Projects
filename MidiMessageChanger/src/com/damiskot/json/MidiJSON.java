package com.damiskot.json;

import java.util.List;

public class MidiJSON {

    private List<JsonThread> jsonThreads;

    public MidiJSON(List<JsonThread> jsonThreads) {
        this.jsonThreads = jsonThreads;
    }

    public List<JsonThread> getJsonThreads() {
        return jsonThreads;
    }

}
