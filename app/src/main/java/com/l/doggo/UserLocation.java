package com.l.doggo;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.auth.User;

public class UserLocation {

    private GeoPoint geoPoint;
    private ServerTimestamp timestamp;
    private User user;

    public UserLocation () {}

    public UserLocation(GeoPoint geoPoint, ServerTimestamp timestamp, User user) {
        this.geoPoint = geoPoint;
        this.timestamp = timestamp;
        this.user = user;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public ServerTimestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ServerTimestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "geoPoint=" + geoPoint +
                ", timestamp=" + timestamp +
                ", user=" + user +
                '}';
    }
}
