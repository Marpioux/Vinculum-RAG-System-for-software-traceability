package org.example;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Method {
    @JsonProperty
    public String body;

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

    public Method(String body, boolean hasComment, boolean hasInnerComment, String signature){
        this.body = body;
        this.hasComment = hasComment;
        this.hasInnerComment = hasInnerComment;
        this.signature = signature;
    }

    public Method(String body,boolean hasComment, String comments, boolean hasInnerComment, String signature, String innerComments){
        this.body = body;
        this.comments = comments;
        this.hasComment = hasComment;
        this.hasInnerComment = hasInnerComment;
        this.signature = signature;
        this.innerComments = innerComments;
    }


}
