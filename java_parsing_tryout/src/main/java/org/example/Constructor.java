package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Constructor {

    @JsonProperty
    String body;

    @JsonProperty
    public boolean hasComment;

    @JsonProperty
    public boolean hasInnerComment;

    @JsonProperty
    public String signature;

    @JsonProperty
    public String comments;

    @JsonProperty
    public String innerComments;

    public Constructor(String body,boolean hasComment, String comments, boolean hasInnerComment, String signature, String innerComments){
        this.body = body;
        this.comments = comments;
        this.hasComment = hasComment;
        this.hasInnerComment = hasInnerComment;
        this.signature = signature;
        this.innerComments = innerComments;
    }
}
