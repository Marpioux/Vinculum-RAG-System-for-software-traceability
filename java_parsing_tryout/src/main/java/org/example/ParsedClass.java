package org.example;
import java.util.List;

public class ParsedClass {
    public List<Method> methods;
    public String signature;

    public ParsedClass(List<Method> methods, String signature){
        this.methods = methods;
        this.signature = signature;
    }
}
