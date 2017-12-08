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
public class Order {

    private String produkDescription;
    private double cost;
    private int quantity;
    private double totalCost;

    public Order() {
    }

    public Order(String produkDescription, double cost, int quantity) {
        this.produkDescription = produkDescription;
        this.cost = cost;
        this.quantity = quantity;
    }

    /**
     * @return the productDescription
     */
    public String getProdukDescription() {
        return produkDescription;
    }

    /**
     * @param productDescription the productDescription to set
     */
    public void setProdukDescription(String productDescription) {
        this.produkDescription = productDescription;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the totalCost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost the totalCost to set
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    

    
}
