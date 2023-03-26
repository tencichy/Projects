
package ets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trailer {

    @SerializedName("attached")
    @Expose
    public Boolean attached;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mass")
    @Expose
    public Double mass;
    @SerializedName("wear")
    @Expose
    public Double wear;
    @SerializedName("placement")
    @Expose
    public Placement_ placement;

    public Boolean getAttached() {
        return attached;
    }

    public void setAttached(Boolean attached) {
        this.attached = attached;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getWear() {
        return wear;
    }

    public void setWear(Double wear) {
        this.wear = wear;
    }

    public Placement_ getPlacement() {
        return placement;
    }

    public void setPlacement(Placement_ placement) {
        this.placement = placement;
    }

}
