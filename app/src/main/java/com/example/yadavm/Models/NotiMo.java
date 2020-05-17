package com.example.yadavm.Models;

public class NotiMo {
    public String notititle;
    public String time;
    public String notimessage;

    public String getNotititle() {
        return notititle;
    }

    public void setNotititle(String notititle) {
        this.notititle = notititle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotimessage() {
        return notimessage;
    }

    public void setNotimessage(String notimessage) {
        this.notimessage = notimessage;
    }

    public NotiMo(String notititle, String time, String notimessage) {
        this.notititle = notititle;
        this.time = time;
        this.notimessage = notimessage;
    }

    public NotiMo() {
    }
}
