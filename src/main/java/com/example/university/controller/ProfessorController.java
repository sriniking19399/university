/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.university.model.*;
import com.example.university.service.ProfessorJpaService;

@RestController
public class ProfessorController {

    @Autowired
    public ProfessorJpaService professorService;

    @GetMapping("/professors")
    public ArrayList<Professor> getAllProfessors() {
        return professorService.getAllProfessors();
    }

    @GetMapping("/professors/{professorId}")
    public Professor getProfessorById(@PathVariable("professorId") int professorId) {
        return professorService.getProfessorById(professorId);
    }

    @PostMapping("/professors")
    public Professor addProfessor(@RequestBody Professor professor) {
        return professorService.addProfessor(professor);
    }

    @PutMapping("/professors/{professorId}")
    public Professor updateProfessor(@PathVariable("professorId") int professorId, @RequestBody Professor professor) {
        return professorService.updateProfessor(professorId, professor);
    }

    @DeleteMapping("/professors/{professorId}")
    public void deleteProfessor(@PathVariable("professorId") int professorId) {
        professorService.deleteProfessor(professorId);
    }

    @GetMapping("/professors/{professorId}/courses")
    public ArrayList<Course> getCourses(@PathVariable("professorId") int professorId) {
        return professorService.getCourses(professorId);
    }

}