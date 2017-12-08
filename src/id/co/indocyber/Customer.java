/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.indocyber;

/**
 *
 * @author user
 */
public class Customer {

private String name;
private String addressline1;
private String addressline2;
private String city;
private int customerId;
private String discountCode;
private double creditLimit;
private String email;
private String fax;
private String phone;
private String state;
private String zip;

    public Customer() {
    }

    public Customer(String name, String addressLine1, String addressLine2, String city, int customerId, String discountCode, double creditLimit, String email, String fax, String phone, String state, String zip) {
        this.name = name;
        this.addressline1 = addressline1;
        this.addressline2 = addressline2;
        this.city = city;
        this.customerId = customerId;
        this.discountCode = discountCode;
        this.creditLimit = creditLimit;
        this.email = email;
        this.fax = fax;
        this.phone = phone;
        this.state = state;
        this.zip = zip;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the addressLine1
     */
    public String getAddressline1() {
        return addressline1;
    }

    /**
     * @param addressLine1 the addressLine1 to set
     */
    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    /**
     * @return the addressLine2
     */
    public String getAddressline2() {
        return addressline2;
    }

    /**
     * @param addressLine2 the addressLine2 to set
     */
    public void setAddressline2(String addressLine2) {
        this.addressline2 = addressLine2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the discountCode
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * @param discountCode the discountCode to set
     */
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    /**
     * @return the creditLimit
     */
    public double getCreditLimit() {
        return creditLimit;
    }

    /**
     * @param creditLimit the creditLimit to set
     */
    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    


}
