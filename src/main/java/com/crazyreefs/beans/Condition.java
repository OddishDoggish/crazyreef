package com.crazyreefs.beans;

public class Condition {
    private String light; // how much light is suggested?
    private String flow; // how much flow is needed?
    private String care; // what is the care level?
    private String safe; // is this reef-safe?

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
    }

    public String getSafe() {
        return safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }
}
