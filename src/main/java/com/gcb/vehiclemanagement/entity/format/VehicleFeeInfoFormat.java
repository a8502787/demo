package com.gcb.vehiclemanagement.entity.format;

public class VehicleFeeInfoFormat {
    private String applyId;
    private String vehicleId;
    private String agent;
    private String startTime;
    private String endTime;
    private double oilFee;
    private double maintenanceFee;
    private double parkingFee;
    private double forfeit;
    private String paymentTime;
    private String parkingPlace;
    private double startKilometers;
    private double endKilometers;

    public VehicleFeeInfoFormat(String applyId, String vehicleId, String agent, String startTime, String endTime, double oilFee,
                                double maintenanceFee, double parkingFee, double forfeit, String paymentTime, String parkingPlace,
                                double startKilometers, double endKilometers) {
        this.applyId = applyId;
        this.vehicleId = vehicleId;
        this.agent = agent;
        this.startTime = startTime;
        this.endTime = endTime;
        this.oilFee = oilFee;
        this.maintenanceFee = maintenanceFee;
        this.parkingFee = parkingFee;
        this.forfeit = forfeit;
        this.paymentTime = paymentTime;
        this.parkingPlace = parkingPlace;
        this.startKilometers = startKilometers;
        this.endKilometers = endKilometers;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getOilFee() {
        return oilFee;
    }

    public void setOilFee(double oilFee) {
        this.oilFee = oilFee;
    }

    public double getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(double maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    public double getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(double parkingFee) {
        this.parkingFee = parkingFee;
    }

    public double getForfeit() {
        return forfeit;
    }

    public void setForfeit(double forfeit) {
        this.forfeit = forfeit;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getParkingPlace() {
        return parkingPlace;
    }

    public void setParkingPlace(String parkingPlace) {
        this.parkingPlace = parkingPlace;
    }

    public double getStartKilometers() {
        return startKilometers;
    }

    public void setStartKilometers(double startKilometers) {
        this.startKilometers = startKilometers;
    }

    public double getEndKilometers() {
        return endKilometers;
    }

    public void setEndKilometers(double endKilometers) {
        this.endKilometers = endKilometers;
    }
}
