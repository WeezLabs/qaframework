package model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Example of DTO object.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class GeoPoint {
    private Number latitude;
    private Number longitude;

    @JsonIgnore
    public GeoPoint(Number latitude, Number longitude) {
        this.latitude = (double) (int) (latitude.doubleValue() * 1000000) / 1000000;
        this.longitude = (double) (int) (longitude.doubleValue() * 1000000) / 1000000;
    }

    public GeoPoint() {
    }

    public Number getLatitude() {
        return latitude;
    }

    @JsonProperty("Latitude")
    public void setLatitude(Number latitude) {
        this.latitude = latitude;
    }

    public Number getLongitude() {
        return longitude;
    }

    @JsonProperty("Longitude")
    public void setLongitude(Number longitude) {
        this.longitude = longitude;
    }
}
