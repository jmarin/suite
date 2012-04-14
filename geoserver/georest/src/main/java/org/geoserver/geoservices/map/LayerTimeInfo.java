package org.geoserver.geoservices.map;

import java.util.List;

public class LayerTimeInfo {

    private String startTimeField;

    private String endTimeField;

    private String trackIdField;

    private List<TimeExtent> timeExtent;

    private TimeReference timeReference;

    private double timeInterval;

    private String timeIntervalUnits;

    public LayerTimeInfo(String startTimeField, String endTimeField, String trackIdField,
            List<TimeExtent> timeExtent, TimeReference timeReference, double timeInterval,
            String timeIntervalUnits) {
        this.startTimeField = startTimeField;
        this.endTimeField = endTimeField;
        this.trackIdField = trackIdField;
        this.timeExtent = timeExtent;
        this.timeReference = timeReference;
        this.timeInterval = timeInterval;
        this.timeIntervalUnits = timeIntervalUnits;
    }

    public String getStartTimeField() {
        return startTimeField;
    }

    public void setStartTimeField(String startTimeField) {
        this.startTimeField = startTimeField;
    }

    public String getEndTimeField() {
        return endTimeField;
    }

    public void setEndTimeField(String endTimeField) {
        this.endTimeField = endTimeField;
    }

    public String getTrackIdField() {
        return trackIdField;
    }

    public void setTrackIdField(String trackIdField) {
        this.trackIdField = trackIdField;
    }

    public List<TimeExtent> getTimeExtent() {
        return timeExtent;
    }

    public void setTimeExtent(List<TimeExtent> timeExtent) {
        this.timeExtent = timeExtent;
    }

    public TimeReference getTimeReference() {
        return timeReference;
    }

    public void setTimeReference(TimeReference timeReference) {
        this.timeReference = timeReference;
    }

    public double getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(double timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getTimeIntervalUnits() {
        return timeIntervalUnits;
    }

    public void setTimeIntervalUnits(String timeIntervalUnits) {
        this.timeIntervalUnits = timeIntervalUnits;
    }

}
