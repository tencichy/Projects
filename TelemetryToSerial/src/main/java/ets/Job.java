
package ets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("income")
    @Expose
    public Integer income;
    @SerializedName("deadlineTime")
    @Expose
    public String deadlineTime;
    @SerializedName("remainingTime")
    @Expose
    public String remainingTime;
    @SerializedName("sourceCity")
    @Expose
    public String sourceCity;
    @SerializedName("sourceCompany")
    @Expose
    public String sourceCompany;
    @SerializedName("destinationCity")
    @Expose
    public String destinationCity;
    @SerializedName("destinationCompany")
    @Expose
    public String destinationCompany;

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getSourceCompany() {
        return sourceCompany;
    }

    public void setSourceCompany(String sourceCompany) {
        this.sourceCompany = sourceCompany;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationCompany() {
        return destinationCompany;
    }

    public void setDestinationCompany(String destinationCompany) {
        this.destinationCompany = destinationCompany;
    }

}
