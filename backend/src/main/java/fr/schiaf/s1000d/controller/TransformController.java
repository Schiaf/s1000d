package fr.schiaf.s1000d.controller;

import fr.schiaf.s1000d.service.ElementService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/transform")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React frontend
public class TransformController {

    private final ElementService elementService;

    public TransformController(ElementService elementService) {
        this.elementService = elementService;
    }

    @PostMapping
    public ResponseEntity<Resource> transformFile(@RequestParam("file") MultipartFile file) {
        try {
            // Sauvegarder temporairement le fichier reçu
            File tempFile = File.createTempFile("uploaded-", ".xml");
            file.transferTo(tempFile);

            // Utiliser ElementService pour traiter le fichier
            elementService.processElementsFromFile(tempFile.getAbsolutePath());

            // Lire le fichier transformé (output.html)
            File transformedFile = new File("output.html");
            if (!transformedFile.exists()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            ByteArrayResource resource = new ByteArrayResource(org.apache.commons.io.FileUtils.readFileToByteArray(transformedFile));

            // Retourner le fichier transformé
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transformed.html")
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

