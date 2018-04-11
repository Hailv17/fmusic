package com.hailv.fmusic.model;


/**
 * Created by msi on 3/20/2018.
 */

public class Songs {
    private String Name,Duration;

    public Songs(String name, String duration) {
        this.Name = name;
        this.Duration = duration;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    @Override
    public String toString() {
        return "Songs{" +
                ", Name='" + Name + '\'' +
                ", Duration='" + Duration + '\'' +
                '}';
    }
}
