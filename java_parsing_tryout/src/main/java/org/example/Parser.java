package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
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
    public static void retrieveClass(CompilationUnit cu){
        //retrieve the class of a file
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(clazz ->
                System.out.println("Class found: " + clazz.getNameAsString())
        );
    }

    /* Retrieve
    the
    methods of the class
     */
    public static void retrieveMethods(CompilationUnit cu){
        cu.findAll(MethodDeclaration.class).forEach(method -> {
                    System.out.println("Method found: " + method.getNameAsString());
                    System.out.println("Method has comment: " + method.getComment().isPresent());
                    System.out.println("Method inner methods: " + method.getBody().get().getAllContainedComments());
                }
        );
    }
}
