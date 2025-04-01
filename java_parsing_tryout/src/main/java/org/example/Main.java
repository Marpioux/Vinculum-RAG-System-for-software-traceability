package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;

import static org.example.Parser.*;
import static org.example.ParsedClass.*;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/java/org/example/Main.java"; // Chemin du fichier à parser
        File aFile = new File(filePath);
        CompilationUnit cu = parseFile(aFile);
        //System.out.println(cu.toString());
        assert cu != null;
        List<ParsedClass> classes = retrieveClass(cu);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(classes);
            System.out.println(json);
        } catch (Exception e) {  // Gère toutes les exceptions possibles
            System.err.println("Erreur lors de la conversion en JSON: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
