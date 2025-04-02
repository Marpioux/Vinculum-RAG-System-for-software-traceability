package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.CompilationUnit;
import netscape.javascript.JSObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.example.Parser.parseFile;
import static org.example.Parser.retrieveClass;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class retrieveAPI {

    public static void main(String[] args) {
        SpringApplication.run(retrieveAPI.class, args);
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }

    @PostMapping("/retrieveJson")
    public String retrieveJson(@RequestBody Map<String, String> request) {
        String filePath = request.get("filePath");
        if (filePath == null || filePath.isEmpty()) {
            return "Erreur : Chemin du fichier non fourni.";
        }

        File aFile = new File(filePath);

        if (!aFile.exists() || !aFile.isFile()) {
            return "Erreur : Le fichier spécifié n'existe pas (" + !aFile.exists() + ") ou n'est pas valide (" + !aFile.isFile() + "). Chemin fourni: " + aFile.toString();
        }

        CompilationUnit cu = parseFile(aFile);
        if (cu == null) {
            return "Erreur : Impossible de parser le fichier.";
        }

        List<ParsedClass> classes = retrieveClass(cu);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(classes);
        } catch (Exception e) {
            return "Erreur lors de la conversion en JSON: " + e.getMessage();
        }
    }

}
