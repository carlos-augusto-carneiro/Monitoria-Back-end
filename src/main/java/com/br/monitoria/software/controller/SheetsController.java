package com.br.monitoria.software.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.monitoria.software.dto.Student;
import com.br.monitoria.software.dto.Student2;
import com.br.monitoria.software.exception.StudentNotFoundException;
import com.br.monitoria.software.service.SheetsService;

@RestController
@RequestMapping("/api")
public class SheetsController {
    @Autowired
    private SheetsService sheetsService;

    @GetMapping("/student")
    public ResponseEntity<?> getStudentData(@RequestParam("studentId") String studentId) {
        try {
            Student student = sheetsService.fetchStudentData(studentId);
            return ResponseEntity.ok(student);
        } catch (IOException | GeneralSecurityException e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar dados do estudante: " + e.getMessage());
        } catch (StudentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/student2")
    public ResponseEntity<?> getStudentData2(@RequestParam("studentId") String studentId) {
        try {
            Student2 student = sheetsService.fetchStudentData2(studentId);
            return ResponseEntity.ok(student);
        } catch (IOException | GeneralSecurityException e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar dados do estudante: " + e.getMessage());
        } catch (StudentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
