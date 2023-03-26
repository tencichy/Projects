
package ets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Game {

    @SerializedName("connected")
    @Expose
    public Boolean connected;
    @SerializedName("paused")
    @Expose
    public Boolean paused;
    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("timeScale")
    @Expose
    public Double timeScale;
    @SerializedName("nextRestStopTime")
    @Expose
    public String nextRestStopTime;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("telemetryPluginVersion")
    @Expose
    public String telemetryPluginVersion;

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Boolean getPaused() {
        return paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(Double timeScale) {
        this.timeScale = timeScale;
    }

    public String getNextRestStopTime() {
        return nextRestStopTime;
    }

    public void setNextRestStopTime(String nextRestStopTime) {
        this.nextRestStopTime = nextRestStopTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTelemetryPluginVersion() {
        return telemetryPluginVersion;
    }

    public void setTelemetryPluginVersion(String telemetryPluginVersion) {
        this.telemetryPluginVersion = telemetryPluginVersion;
    }

}
