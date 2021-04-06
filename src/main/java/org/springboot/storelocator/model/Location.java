
package org.springboot.storelocator.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "LAT",
    "LNG"
})
@Generated("jsonschema2pojo")
public class Location {

    @JsonProperty("LAT")
    private String lat;
    @JsonProperty("LNG")
    private String lng;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("LAT")
    public String getLat() {
        return lat;
    }

    @JsonProperty("LAT")
    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("LNG")
    public String getLng() {
        return lng;
    }

    @JsonProperty("LNG")
    public void setLng(String lng) {
        this.lng = lng;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
