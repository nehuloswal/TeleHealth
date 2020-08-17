package com.example.telehealth;

import java.util.Map;

public class ModelNY {
    private String Name;
    private String Hospital;
    private long ID;
    private Map<String,Boolean> Slot;
    public ModelNY(String Hospital, long ID, String Name) {
        this.Name = Name;
        this.Hospital = Hospital;
        this.ID = ID;
        this.Slot = Slot;
    }
    public ModelNY(){}
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getHospital() {
        return Hospital;
    }
    public void setHospital(String hospital) {
        Hospital = hospital;
    }
    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }
    public Map<String, Boolean> getSlot(){return Slot;}
}
