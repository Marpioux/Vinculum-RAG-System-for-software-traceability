package org.example;
import java.util.List;

public class ParsedClass {
    public List<Method> methods;

    public List<Constructor> constructors;
    public String signature;

    public List<String> variables;

    public ParsedClass(List<Method> methods, String signature, List<Constructor> constructors, List<String> variables){
        this.methods = methods;
        this.signature = signature;
        this.constructors = constructors;
        this.variables = variables;
    }
}
