
package ets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EtsData {

    @SerializedName("game")
    @Expose
    public Game game;
    @SerializedName("truck")
    @Expose
    public Truck truck;
    @SerializedName("trailer")
    @Expose
    public Trailer trailer;
    @SerializedName("job")
    @Expose
    public Job job;
    @SerializedName("navigation")
    @Expose
    public Navigation navigation;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

}
