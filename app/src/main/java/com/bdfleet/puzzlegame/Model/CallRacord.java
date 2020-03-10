package com.bdfleet.puzzlegame.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallRacord {

    public CallRacord() {
    }

    @SerializedName("from")
    @Expose
    private String from;

    @SerializedName("to")
    @Expose
    private String to;

    @SerializedName("start_at")
    @Expose
    private String start_at;

    @SerializedName("end_at")
    @Expose
    private String end_at;

    @SerializedName("call_type")
    @Expose
    private int call_type;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public int getCall_type() {
        return call_type;
    }

    public void setCall_type(int call_type) {
        this.call_type = call_type;
    }

    public CallRacord(String from, String to,int call_type, String start_at, String end_at) {
        this.from = from;
        this.to = to;
        this.start_at = start_at;
        this.end_at = end_at;
        this.call_type = call_type;
    }
}
