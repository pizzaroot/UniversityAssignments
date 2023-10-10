package org.dfpl.lecture.database.assignment.assignment21011793;

public class GameFacility {
    private int serialNumber;
    private String registrationDate;
    private String businessName;
    private String industryType;
    private String address;
    private double latitude;

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getIndustryType() {
        return industryType;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private double longitude;

    public GameFacility(int serialNumber, String registrationDate, String businessName, String industryType, String address, double latitude, double longitude) {
        this.serialNumber = serialNumber;
        this.registrationDate = registrationDate;
        this.businessName = businessName;
        this.industryType = industryType;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GameFacility{" +
                "serialNumber='" + serialNumber + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", businessName='" + businessName + '\'' +
                ", industryType='" + industryType + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
