package org.example;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Method {
    @JsonProperty
    public String body;

    @JsonProperty
    public boolean hasComment;

    @JsonProperty
    public boolean innerComment;

    @JsonProperty
    public String signature;

    public Method(String body, boolean hasComment, boolean innerComment, String signature){
        this.body = body;
        this.hasComment = hasComment;
        this.innerComment = innerComment;
        this.signature = signature;
    }
}
