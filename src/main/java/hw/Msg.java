package hw;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Msg {
    private String name;

    public Msg() {
        // Jackson deserialization
    }

    public Msg(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return "Hello " + name;
    }
}
