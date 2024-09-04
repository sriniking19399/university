/*
 *
 * You can use the following import statements
 * 
 * import javax.persistence.*;
 * 
 */

// Write your code here
package com.example.university.model;

import javax.persistence.*;

@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int professorId;
    @Column(name = "name")
    private String professorName;
    @Column(name = "department")
    private String department;

    public Professor() {
    }

    public Professor(int professorId, String professorName, String department) {
        this.professorId = professorId;
        this.professorName = professorName;
        this.department = department;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setPrrofessorId(int professorId) {
        this.professorId = professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}