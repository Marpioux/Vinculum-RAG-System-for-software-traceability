package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class Parser {
    public static CompilationUnit parseFile(File aFile){
        try {
            return StaticJavaParser.parse(aFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<ParsedClass> retrieveClass(CompilationUnit cu){
        //retrieve the class of a file
        List<ParsedClass> classList = new ArrayList<>();
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(clazz -> {
                System.out.println("Class found: " + clazz.getNameAsString());
                classList.add(new ParsedClass(retrieveMethods(cu),clazz.getNameAsString()));
        }
        );
        // Another comment
        return classList;
    }

    /* Retrieve
    the
    methods of the class
     */
    public static List<Method> retrieveMethods(CompilationUnit cu) {
        List<Method> classMethods = new ArrayList<>();

        cu.findAll(MethodDeclaration.class).forEach(method -> {
            boolean hasInnerComments = method.getBody()
                    .map(body -> !body.getAllContainedComments().isEmpty())
                    .orElse(false);

            String comment =  method.getComment().isPresent() ? method.getComment().toString() : null;
            String innerComments = hasInnerComments ? method.getBody().map(Node::getAllContainedComments).toString() : null;
            Method parsedMethod = new Method(
                        method.getBody().toString(),
                        method.getComment().isPresent(),
                        comment,
                        hasInnerComments,
                        method.getDeclarationAsString(true, false, false),
                        innerComments
                );

            System.out.println("Method found: " + method.getNameAsString());
            System.out.println("Method has comment: " + method.getComment().isPresent());
            System.out.println("Method inner methods: " + hasInnerComments);

            classMethods.add(parsedMethod);
        });

        return classMethods;
    }
}
