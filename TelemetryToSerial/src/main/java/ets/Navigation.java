
package ets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Navigation {

    @SerializedName("estimatedTime")
    @Expose
    public String estimatedTime;
    @SerializedName("estimatedDistance")
    @Expose
    public Integer estimatedDistance;
    @SerializedName("speedLimit")
    @Expose
    public Integer speedLimit;

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Integer getEstimatedDistance() {
        return estimatedDistance;
    }

    public void setEstimatedDistance(Integer estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }

}
