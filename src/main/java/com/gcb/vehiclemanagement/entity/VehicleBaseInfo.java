package com.gcb.vehiclemanagement.entity;

import java.sql.Timestamp;

public class VehicleBaseInfo {
    private String vehicleId;
    private int seatNumber;
    private String vehicleType;
    private String vehicleColor;
    private Timestamp purchaseTime;
    private Timestamp insuranceStart;
    private String vehiclePhoto;
    private String status;
    private Timestamp returnTime;
    private String parkingPlace;
    private double kilometers;

    public VehicleBaseInfo() {
        super();
    }

    public VehicleBaseInfo(String vehicleId, int seatNumber, String vehicleType, String vehicleColor, Timestamp purchaseTime,
                           Timestamp insuranceStart, String vehiclePhoto, String status, Timestamp returnTime, String parkingPlace,
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

    public Timestamp getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Timestamp purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Timestamp getInsuranceStart() {
        return insuranceStart;
    }

    public void setInsuranceStart(Timestamp insuranceStart) {
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

    public Timestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
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

    @Override
    public String toString() {
        return "VehicleBaseInfo{" +
                "vehicleId='" + vehicleId + '\'' +
                ", seatNumber=" + seatNumber +
                ", vehicleType='" + vehicleType + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", purchaseTime=" + purchaseTime +
                ", insuranceStart=" + insuranceStart +
                ", vehiclePhoto='" + vehiclePhoto + '\'' +
                ", status='" + status + '\'' +
                ", returnTime=" + returnTime +
                ", parkingPlace='" + parkingPlace + '\'' +
                ", kilometers=" + kilometers +
                '}';
    }
}
