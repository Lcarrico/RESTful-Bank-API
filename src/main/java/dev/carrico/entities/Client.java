package dev.carrico.entities;

import java.util.HashSet;
import java.util.Set;

public class Client {
    private int cliendId;
    private String fName;
    private String lName;

    public Client(){
        this.cliendId = 0;
        this.fName = "";
        this.lName = "";
    }

    public Client(int clientId, String fName, String lName) {
        this.cliendId = clientId;
        this.fName = fName;
        this.lName = lName;
    }

    public Client(String fName, String lName) {
        this.cliendId = 0;
        this.fName = fName;
        this.lName = lName;
    }

    public int getCliendId() {
        return cliendId;
    }

    public void setCliendId(int cliendId) {
        this.cliendId = cliendId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @Override
    public String toString() {
        return "Client{" +
                "cliendId=" + cliendId +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                '}';
    }
}
