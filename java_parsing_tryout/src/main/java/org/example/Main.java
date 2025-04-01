package org.example;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;

import static org.example.Parser.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/java/org/example/Main.java"; // Chemin du fichier à parser
        File aFile = new File(filePath);
        CompilationUnit cu = parseFile(aFile);
        //System.out.println(cu.toString());
        assert cu != null;
        retrieveClass(cu);
        retrieveMethods(cu);
    }
}
