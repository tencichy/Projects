
package ets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Truck {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("make")
    @Expose
    public String make;
    @SerializedName("model")
    @Expose
    public String model;
    @SerializedName("speed")
    @Expose
    public Double speed;
    @SerializedName("cruiseControlSpeed")
    @Expose
    public Double cruiseControlSpeed;
    @SerializedName("cruiseControlOn")
    @Expose
    public Boolean cruiseControlOn;
    @SerializedName("odometer")
    @Expose
    public Double odometer;
    @SerializedName("gear")
    @Expose
    public Integer gear;
    @SerializedName("displayedGear")
    @Expose
    public Integer displayedGear;
    @SerializedName("forwardGears")
    @Expose
    public Integer forwardGears;
    @SerializedName("reverseGears")
    @Expose
    public Integer reverseGears;
    @SerializedName("shifterType")
    @Expose
    public String shifterType;
    @SerializedName("engineRpm")
    @Expose
    public Double engineRpm;
    @SerializedName("engineRpmMax")
    @Expose
    public Double engineRpmMax;
    @SerializedName("fuel")
    @Expose
    public Double fuel;
    @SerializedName("fuelCapacity")
    @Expose
    public Double fuelCapacity;
    @SerializedName("fuelAverageConsumption")
    @Expose
    public Double fuelAverageConsumption;
    @SerializedName("fuelWarningFactor")
    @Expose
    public Double fuelWarningFactor;
    @SerializedName("fuelWarningOn")
    @Expose
    public Boolean fuelWarningOn;
    @SerializedName("wearEngine")
    @Expose
    public Double wearEngine;
    @SerializedName("wearTransmission")
    @Expose
    public Double wearTransmission;
    @SerializedName("wearCabin")
    @Expose
    public Double wearCabin;
    @SerializedName("wearChassis")
    @Expose
    public Double wearChassis;
    @SerializedName("wearWheels")
    @Expose
    public Double wearWheels;
    @SerializedName("userSteer")
    @Expose
    public Double userSteer;
    @SerializedName("userThrottle")
    @Expose
    public Double userThrottle;
    @SerializedName("userBrake")
    @Expose
    public Double userBrake;
    @SerializedName("userClutch")
    @Expose
    public Double userClutch;
    @SerializedName("gameSteer")
    @Expose
    public Double gameSteer;
    @SerializedName("gameThrottle")
    @Expose
    public Double gameThrottle;
    @SerializedName("gameBrake")
    @Expose
    public Double gameBrake;
    @SerializedName("gameClutch")
    @Expose
    public Double gameClutch;
    @SerializedName("shifterSlot")
    @Expose
    public Integer shifterSlot;
    @SerializedName("engineOn")
    @Expose
    public Boolean engineOn;
    @SerializedName("electricOn")
    @Expose
    public Boolean electricOn;
    @SerializedName("wipersOn")
    @Expose
    public Boolean wipersOn;
    @SerializedName("retarderBrake")
    @Expose
    public Integer retarderBrake;
    @SerializedName("retarderStepCount")
    @Expose
    public Integer retarderStepCount;
    @SerializedName("parkBrakeOn")
    @Expose
    public Boolean parkBrakeOn;
    @SerializedName("motorBrakeOn")
    @Expose
    public Boolean motorBrakeOn;
    @SerializedName("brakeTemperature")
    @Expose
    public Double brakeTemperature;
    @SerializedName("adblue")
    @Expose
    public Double adblue;
    @SerializedName("adblueCapacity")
    @Expose
    public Double adblueCapacity;
    @SerializedName("adblueAverageConsumpton")
    @Expose
    public Double adblueAverageConsumpton;
    @SerializedName("adblueWarningOn")
    @Expose
    public Boolean adblueWarningOn;
    @SerializedName("airPressure")
    @Expose
    public Double airPressure;
    @SerializedName("airPressureWarningOn")
    @Expose
    public Boolean airPressureWarningOn;
    @SerializedName("airPressureWarningValue")
    @Expose
    public Double airPressureWarningValue;
    @SerializedName("airPressureEmergencyOn")
    @Expose
    public Boolean airPressureEmergencyOn;
    @SerializedName("airPressureEmergencyValue")
    @Expose
    public Double airPressureEmergencyValue;
    @SerializedName("oilTemperature")
    @Expose
    public Double oilTemperature;
    @SerializedName("oilPressure")
    @Expose
    public Double oilPressure;
    @SerializedName("oilPressureWarningOn")
    @Expose
    public Boolean oilPressureWarningOn;
    @SerializedName("oilPressureWarningValue")
    @Expose
    public Double oilPressureWarningValue;
    @SerializedName("waterTemperature")
    @Expose
    public Double waterTemperature;
    @SerializedName("waterTemperatureWarningOn")
    @Expose
    public Boolean waterTemperatureWarningOn;
    @SerializedName("waterTemperatureWarningValue")
    @Expose
    public Double waterTemperatureWarningValue;
    @SerializedName("batteryVoltage")
    @Expose
    public Double batteryVoltage;
    @SerializedName("batteryVoltageWarningOn")
    @Expose
    public Boolean batteryVoltageWarningOn;
    @SerializedName("batteryVoltageWarningValue")
    @Expose
    public Double batteryVoltageWarningValue;
    @SerializedName("lightsDashboardValue")
    @Expose
    public Double lightsDashboardValue;
    @SerializedName("lightsDashboardOn")
    @Expose
    public Boolean lightsDashboardOn;
    @SerializedName("blinkerLeftActive")
    @Expose
    public Boolean blinkerLeftActive;
    @SerializedName("blinkerRightActive")
    @Expose
    public Boolean blinkerRightActive;
    @SerializedName("blinkerLeftOn")
    @Expose
    public Boolean blinkerLeftOn;
    @SerializedName("blinkerRightOn")
    @Expose
    public Boolean blinkerRightOn;
    @SerializedName("lightsParkingOn")
    @Expose
    public Boolean lightsParkingOn;
    @SerializedName("lightsBeamLowOn")
    @Expose
    public Boolean lightsBeamLowOn;
    @SerializedName("lightsBeamHighOn")
    @Expose
    public Boolean lightsBeamHighOn;
    @SerializedName("lightsAuxFrontOn")
    @Expose
    public Boolean lightsAuxFrontOn;
    @SerializedName("lightsAuxRoofOn")
    @Expose
    public Boolean lightsAuxRoofOn;
    @SerializedName("lightsBeaconOn")
    @Expose
    public Boolean lightsBeaconOn;
    @SerializedName("lightsBrakeOn")
    @Expose
    public Boolean lightsBrakeOn;
    @SerializedName("lightsReverseOn")
    @Expose
    public Boolean lightsReverseOn;
    @SerializedName("placement")
    @Expose
    public Placement placement;
    @SerializedName("acceleration")
    @Expose
    public Acceleration acceleration;
    @SerializedName("head")
    @Expose
    public Head head;
    @SerializedName("cabin")
    @Expose
    public Cabin cabin;
    @SerializedName("hook")
    @Expose
    public Hook hook;

    @Override
    public String toString() {
        return "Id: " + id + "\nMake: " + make + "\nModel: " + model + "\nSpeed: " + speed + "\nCruise Control Speed: " + cruiseControlSpeed +
        "\nCruise Control On: " + cruiseControlOn + "\nOdometer: " + odometer + "\nGear: " + gear + "\nDisplayed Gear: " + displayedGear + "\nForward Gears: " + forwardGears +
        "\nReverse Gears: " + reverseGears + "\nShifter Type: " + shifterType + "\nEngine RPM: " + engineRpm + "\nEngine max RPM: " + engineRpmMax + "\nFuel: " + fuel +
        "\nFuel Capacity: " + fuelCapacity + "\nFuel Average Consumption: " + fuelAverageConsumption + "\nFuel Warning Factor: " + fuelWarningFactor + "\nFuel Warning On: " + fuelWarningOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getCruiseControlSpeed() {
        return cruiseControlSpeed;
    }

    public void setCruiseControlSpeed(Double cruiseControlSpeed) {
        this.cruiseControlSpeed = cruiseControlSpeed;
    }

    public Boolean getCruiseControlOn() {
        return cruiseControlOn;
    }

    public void setCruiseControlOn(Boolean cruiseControlOn) {
        this.cruiseControlOn = cruiseControlOn;
    }

    public Double getOdometer() {
        return odometer;
    }

    public void setOdometer(Double odometer) {
        this.odometer = odometer;
    }

    public Integer getGear() {
        return gear;
    }

    public void setGear(Integer gear) {
        this.gear = gear;
    }

    public Integer getDisplayedGear() {
        return displayedGear;
    }

    public void setDisplayedGear(Integer displayedGear) {
        this.displayedGear = displayedGear;
    }

    public Integer getForwardGears() {
        return forwardGears;
    }

    public void setForwardGears(Integer forwardGears) {
        this.forwardGears = forwardGears;
    }

    public Integer getReverseGears() {
        return reverseGears;
    }

    public void setReverseGears(Integer reverseGears) {
        this.reverseGears = reverseGears;
    }

    public String getShifterType() {
        return shifterType;
    }

    public void setShifterType(String shifterType) {
        this.shifterType = shifterType;
    }

    public Double getEngineRpm() {
        return engineRpm;
    }

    public void setEngineRpm(Double engineRpm) {
        this.engineRpm = engineRpm;
    }

    public Double getEngineRpmMax() {
        return engineRpmMax;
    }

    public void setEngineRpmMax(Double engineRpmMax) {
        this.engineRpmMax = engineRpmMax;
    }

    public Double getFuel() {
        return fuel;
    }

    public void setFuel(Double fuel) {
        this.fuel = fuel;
    }

    public Double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public Double getFuelAverageConsumption() {
        return fuelAverageConsumption;
    }

    public void setFuelAverageConsumption(Double fuelAverageConsumption) {
        this.fuelAverageConsumption = fuelAverageConsumption;
    }

    public Double getFuelWarningFactor() {
        return fuelWarningFactor;
    }

    public void setFuelWarningFactor(Double fuelWarningFactor) {
        this.fuelWarningFactor = fuelWarningFactor;
    }

    public Boolean getFuelWarningOn() {
        return fuelWarningOn;
    }

    public void setFuelWarningOn(Boolean fuelWarningOn) {
        this.fuelWarningOn = fuelWarningOn;
    }

    public Double getWearEngine() {
        return wearEngine;
    }

    public void setWearEngine(Double wearEngine) {
        this.wearEngine = wearEngine;
    }

    public Double getWearTransmission() {
        return wearTransmission;
    }

    public void setWearTransmission(Double wearTransmission) {
        this.wearTransmission = wearTransmission;
    }

    public Double getWearCabin() {
        return wearCabin;
    }

    public void setWearCabin(Double wearCabin) {
        this.wearCabin = wearCabin;
    }

    public Double getWearChassis() {
        return wearChassis;
    }

    public void setWearChassis(Double wearChassis) {
        this.wearChassis = wearChassis;
    }

    public Double getWearWheels() {
        return wearWheels;
    }

    public void setWearWheels(Double wearWheels) {
        this.wearWheels = wearWheels;
    }

    public Double getUserSteer() {
        return userSteer;
    }

    public void setUserSteer(Double userSteer) {
        this.userSteer = userSteer;
    }

    public Double getUserThrottle() {
        return userThrottle;
    }

    public void setUserThrottle(Double userThrottle) {
        this.userThrottle = userThrottle;
    }

    public Double getUserBrake() {
        return userBrake;
    }

    public void setUserBrake(Double userBrake) {
        this.userBrake = userBrake;
    }

    public Double getUserClutch() {
        return userClutch;
    }

    public void setUserClutch(Double userClutch) {
        this.userClutch = userClutch;
    }

    public Double getGameSteer() {
        return gameSteer;
    }

    public void setGameSteer(Double gameSteer) {
        this.gameSteer = gameSteer;
    }

    public Double getGameThrottle() {
        return gameThrottle;
    }

    public void setGameThrottle(Double gameThrottle) {
        this.gameThrottle = gameThrottle;
    }

    public Double getGameBrake() {
        return gameBrake;
    }

    public void setGameBrake(Double gameBrake) {
        this.gameBrake = gameBrake;
    }

    public Double getGameClutch() {
        return gameClutch;
    }

    public void setGameClutch(Double gameClutch) {
        this.gameClutch = gameClutch;
    }

    public Integer getShifterSlot() {
        return shifterSlot;
    }

    public void setShifterSlot(Integer shifterSlot) {
        this.shifterSlot = shifterSlot;
    }

    public Boolean getEngineOn() {
        return engineOn;
    }

    public void setEngineOn(Boolean engineOn) {
        this.engineOn = engineOn;
    }

    public Boolean getElectricOn() {
        return electricOn;
    }

    public void setElectricOn(Boolean electricOn) {
        this.electricOn = electricOn;
    }

    public Boolean getWipersOn() {
        return wipersOn;
    }

    public void setWipersOn(Boolean wipersOn) {
        this.wipersOn = wipersOn;
    }

    public Integer getRetarderBrake() {
        return retarderBrake;
    }

    public void setRetarderBrake(Integer retarderBrake) {
        this.retarderBrake = retarderBrake;
    }

    public Integer getRetarderStepCount() {
        return retarderStepCount;
    }

    public void setRetarderStepCount(Integer retarderStepCount) {
        this.retarderStepCount = retarderStepCount;
    }

    public Boolean getParkBrakeOn() {
        return parkBrakeOn;
    }

    public void setParkBrakeOn(Boolean parkBrakeOn) {
        this.parkBrakeOn = parkBrakeOn;
    }

    public Boolean getMotorBrakeOn() {
        return motorBrakeOn;
    }

    public void setMotorBrakeOn(Boolean motorBrakeOn) {
        this.motorBrakeOn = motorBrakeOn;
    }

    public Double getBrakeTemperature() {
        return brakeTemperature;
    }

    public void setBrakeTemperature(Double brakeTemperature) {
        this.brakeTemperature = brakeTemperature;
    }

    public Double getAdblue() {
        return adblue;
    }

    public void setAdblue(Double adblue) {
        this.adblue = adblue;
    }

    public Double getAdblueCapacity() {
        return adblueCapacity;
    }

    public void setAdblueCapacity(Double adblueCapacity) {
        this.adblueCapacity = adblueCapacity;
    }

    public Double getAdblueAverageConsumpton() {
        return adblueAverageConsumpton;
    }

    public void setAdblueAverageConsumpton(Double adblueAverageConsumpton) {
        this.adblueAverageConsumpton = adblueAverageConsumpton;
    }

    public Boolean getAdblueWarningOn() {
        return adblueWarningOn;
    }

    public void setAdblueWarningOn(Boolean adblueWarningOn) {
        this.adblueWarningOn = adblueWarningOn;
    }

    public Double getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(Double airPressure) {
        this.airPressure = airPressure;
    }

    public Boolean getAirPressureWarningOn() {
        return airPressureWarningOn;
    }

    public void setAirPressureWarningOn(Boolean airPressureWarningOn) {
        this.airPressureWarningOn = airPressureWarningOn;
    }

    public Double getAirPressureWarningValue() {
        return airPressureWarningValue;
    }

    public void setAirPressureWarningValue(Double airPressureWarningValue) {
        this.airPressureWarningValue = airPressureWarningValue;
    }

    public Boolean getAirPressureEmergencyOn() {
        return airPressureEmergencyOn;
    }

    public void setAirPressureEmergencyOn(Boolean airPressureEmergencyOn) {
        this.airPressureEmergencyOn = airPressureEmergencyOn;
    }

    public Double getAirPressureEmergencyValue() {
        return airPressureEmergencyValue;
    }

    public void setAirPressureEmergencyValue(Double airPressureEmergencyValue) {
        this.airPressureEmergencyValue = airPressureEmergencyValue;
    }

    public Double getOilTemperature() {
        return oilTemperature;
    }

    public void setOilTemperature(Double oilTemperature) {
        this.oilTemperature = oilTemperature;
    }

    public Double getOilPressure() {
        return oilPressure;
    }

    public void setOilPressure(Double oilPressure) {
        this.oilPressure = oilPressure;
    }

    public Boolean getOilPressureWarningOn() {
        return oilPressureWarningOn;
    }

    public void setOilPressureWarningOn(Boolean oilPressureWarningOn) {
        this.oilPressureWarningOn = oilPressureWarningOn;
    }

    public Double getOilPressureWarningValue() {
        return oilPressureWarningValue;
    }

    public void setOilPressureWarningValue(Double oilPressureWarningValue) {
        this.oilPressureWarningValue = oilPressureWarningValue;
    }

    public Double getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(Double waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public Boolean getWaterTemperatureWarningOn() {
        return waterTemperatureWarningOn;
    }

    public void setWaterTemperatureWarningOn(Boolean waterTemperatureWarningOn) {
        this.waterTemperatureWarningOn = waterTemperatureWarningOn;
    }

    public Double getWaterTemperatureWarningValue() {
        return waterTemperatureWarningValue;
    }

    public void setWaterTemperatureWarningValue(Double waterTemperatureWarningValue) {
        this.waterTemperatureWarningValue = waterTemperatureWarningValue;
    }

    public Double getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(Double batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public Boolean getBatteryVoltageWarningOn() {
        return batteryVoltageWarningOn;
    }

    public void setBatteryVoltageWarningOn(Boolean batteryVoltageWarningOn) {
        this.batteryVoltageWarningOn = batteryVoltageWarningOn;
    }

    public Double getBatteryVoltageWarningValue() {
        return batteryVoltageWarningValue;
    }

    public void setBatteryVoltageWarningValue(Double batteryVoltageWarningValue) {
        this.batteryVoltageWarningValue = batteryVoltageWarningValue;
    }

    public Double getLightsDashboardValue() {
        return lightsDashboardValue;
    }

    public void setLightsDashboardValue(Double lightsDashboardValue) {
        this.lightsDashboardValue = lightsDashboardValue;
    }

    public Boolean getLightsDashboardOn() {
        return lightsDashboardOn;
    }

    public void setLightsDashboardOn(Boolean lightsDashboardOn) {
        this.lightsDashboardOn = lightsDashboardOn;
    }

    public Boolean getBlinkerLeftActive() {
        return blinkerLeftActive;
    }

    public void setBlinkerLeftActive(Boolean blinkerLeftActive) {
        this.blinkerLeftActive = blinkerLeftActive;
    }

    public Boolean getBlinkerRightActive() {
        return blinkerRightActive;
    }

    public void setBlinkerRightActive(Boolean blinkerRightActive) {
        this.blinkerRightActive = blinkerRightActive;
    }

    public Boolean getBlinkerLeftOn() {
        return blinkerLeftOn;
    }

    public void setBlinkerLeftOn(Boolean blinkerLeftOn) {
        this.blinkerLeftOn = blinkerLeftOn;
    }

    public Boolean getBlinkerRightOn() {
        return blinkerRightOn;
    }

    public void setBlinkerRightOn(Boolean blinkerRightOn) {
        this.blinkerRightOn = blinkerRightOn;
    }

    public Boolean getLightsParkingOn() {
        return lightsParkingOn;
    }

    public void setLightsParkingOn(Boolean lightsParkingOn) {
        this.lightsParkingOn = lightsParkingOn;
    }

    public Boolean getLightsBeamLowOn() {
        return lightsBeamLowOn;
    }

    public void setLightsBeamLowOn(Boolean lightsBeamLowOn) {
        this.lightsBeamLowOn = lightsBeamLowOn;
    }

    public Boolean getLightsBeamHighOn() {
        return lightsBeamHighOn;
    }

    public void setLightsBeamHighOn(Boolean lightsBeamHighOn) {
        this.lightsBeamHighOn = lightsBeamHighOn;
    }

    public Boolean getLightsAuxFrontOn() {
        return lightsAuxFrontOn;
    }

    public void setLightsAuxFrontOn(Boolean lightsAuxFrontOn) {
        this.lightsAuxFrontOn = lightsAuxFrontOn;
    }

    public Boolean getLightsAuxRoofOn() {
        return lightsAuxRoofOn;
    }

    public void setLightsAuxRoofOn(Boolean lightsAuxRoofOn) {
        this.lightsAuxRoofOn = lightsAuxRoofOn;
    }

    public Boolean getLightsBeaconOn() {
        return lightsBeaconOn;
    }

    public void setLightsBeaconOn(Boolean lightsBeaconOn) {
        this.lightsBeaconOn = lightsBeaconOn;
    }

    public Boolean getLightsBrakeOn() {
        return lightsBrakeOn;
    }

    public void setLightsBrakeOn(Boolean lightsBrakeOn) {
        this.lightsBrakeOn = lightsBrakeOn;
    }

    public Boolean getLightsReverseOn() {
        return lightsReverseOn;
    }

    public void setLightsReverseOn(Boolean lightsReverseOn) {
        this.lightsReverseOn = lightsReverseOn;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    public Acceleration getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Acceleration acceleration) {
        this.acceleration = acceleration;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

    public Hook getHook() {
        return hook;
    }

    public void setHook(Hook hook) {
        this.hook = hook;
    }

}
