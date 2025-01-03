package com.Antiprosopia.dealership;

import lombok.Data;

@Data
public class DealershipDTO {
    private Integer dealershipId;
    private String afm;
    private String name;
    private String owner;
    private String password;

    // Getters and Setters
    public Integer getDealershipId() {
        return dealershipId;
    }

    public void setDealershipId(Integer dealershipId) {
        this.dealershipId = dealershipId;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
