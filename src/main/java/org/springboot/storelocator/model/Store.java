
package org.springboot.storelocator.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "STORE_ID",
    "STORE_NAME",
    "PHONE",
    "ADDRESS1",
    "CITY",
    "COUNTRY",
    "ZIPCODE",
    "STLOCATTR",
    "LOCATION",
    "COUNTRYCODE"
})
@Generated("jsonschema2pojo")
public class Store {

    @JsonProperty("STORE_ID")
    private Integer storeId;
    @JsonProperty("STORE_NAME")    
    private String storeName;
    @JsonProperty("PHONE")
    private String phone;
    @JsonProperty("ADDRESS1")
    private String address1;
    @JsonProperty("CITY")
    private String city;
    @JsonProperty("COUNTRY")
    private String country;
    @JsonProperty("ZIPCODE")
    private String zipcode;
    @JsonProperty("STLOCATTR")
    private Stlocattr stlocattr;
    @JsonProperty("LOCATION")
    private Location location;
    @JsonProperty("COUNTRYCODE")
    private String countrycode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("STORE_ID")
    
    public Integer getStoreId() {
        return storeId;
    }

    @JsonProperty("STORE_ID")
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @JsonProperty("STORE_NAME")
    public String getStoreName() {
        return storeName;
    }

    @JsonProperty("STORE_NAME")
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @JsonProperty("PHONE")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("PHONE")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("ADDRESS1")
    public String getAddress1() {
        return address1;
    }

    @JsonProperty("ADDRESS1")
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @JsonProperty("CITY")
    public String getCity() {
        return city;
    }

    @JsonProperty("CITY")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("COUNTRY")
    public String getCountry() {
        return country;
    }

    @JsonProperty("COUNTRY")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("ZIPCODE")
    public String getZipcode() {
        return zipcode;
    }

    @JsonProperty("ZIPCODE")
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @JsonProperty("STLOCATTR")
    public Stlocattr getStlocattr() {
        return stlocattr;
    }

    @JsonProperty("STLOCATTR")
    public void setStlocattr(Stlocattr stlocattr) {
        this.stlocattr = stlocattr;
    }

    @JsonProperty("LOCATION")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("LOCATION")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("COUNTRYCODE")
    public String getCountrycode() {
        return countrycode;
    }

    @JsonProperty("COUNTRYCODE")
    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
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
