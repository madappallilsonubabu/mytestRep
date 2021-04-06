
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
    "LANGUAGE_ID",
    "OPENINGHOURS"
})
@Generated("jsonschema2pojo")
public class Stlocattr {

    @JsonProperty("LANGUAGE_ID")
    private Integer languageId;
    @JsonProperty("OPENINGHOURS")
    private String openinghours;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("LANGUAGE_ID")
    public Integer getLanguageId() {
        return languageId;
    }

    @JsonProperty("LANGUAGE_ID")
    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    @JsonProperty("OPENINGHOURS")
    public String getOpeninghours() {
        return openinghours;
    }

    @JsonProperty("OPENINGHOURS")
    public void setOpeninghours(String openinghours) {
        this.openinghours = openinghours;
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
