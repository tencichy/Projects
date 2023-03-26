
package ets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Placement {

    @SerializedName("x")
    @Expose
    public Double x;
    @SerializedName("y")
    @Expose
    public Double y;
    @SerializedName("z")
    @Expose
    public Double z;
    @SerializedName("heading")
    @Expose
    public Double heading;
    @SerializedName("pitch")
    @Expose
    public Double pitch;
    @SerializedName("roll")
    @Expose
    public Double roll;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Double getPitch() {
        return pitch;
    }

    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    public Double getRoll() {
        return roll;
    }

    public void setRoll(Double roll) {
        this.roll = roll;
    }

}
