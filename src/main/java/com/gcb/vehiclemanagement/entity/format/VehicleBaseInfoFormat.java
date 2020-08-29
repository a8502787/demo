package com.gcb.vehiclemanagement.entity.format;

public class VehicleBaseInfoFormat {
    private String vehicleId;
    private int seatNumber;
    private String vehicleType;
    private String vehicleColor;
    private String purchaseTime;
    private String insuranceStart;
    private String vehiclePhoto;
    private String status;
    private String returnTime;
    private String parkingPlace;
    private double kilometers;

    public VehicleBaseInfoFormat(String vehicleId, int seatNumber, String vehicleType, String vehicleColor, String purchaseTime,
                                 String insuranceStart, String vehiclePhoto, String status, String returnTime, String parkingPlace,
                                 double kilometers) {
        this.vehicleId = vehicleId;
        this.seatNumber = seatNumber;
        this.vehicleType = vehicleType;
        this.vehicleColor = vehicleColor;
        this.purchaseTime = purchaseTime;
        this.insuranceStart = insuranceStart;
        this.vehiclePhoto = vehiclePhoto;
        this.status = status;
        this.returnTime = returnTime;
        this.parkingPlace = parkingPlace;
        this.kilometers = kilometers;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getInsuranceStart() {
        return insuranceStart;
    }

    public void setInsuranceStart(String insuranceStart) {
        this.insuranceStart = insuranceStart;
    }

    public String getVehiclePhoto() {
        return vehiclePhoto;
    }

    public void setVehiclePhoto(String vehiclePhoto) {
        this.vehiclePhoto = vehiclePhoto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getParkingPlace() {
        return parkingPlace;
    }

    public void setParkingPlace(String parkingPlace) {
        this.parkingPlace = parkingPlace;
    }

    public double getKilometers() {
        return kilometers;
    }

    public void setKilometers(double kilometers) {
        this.kilometers = kilometers;
    }
}
