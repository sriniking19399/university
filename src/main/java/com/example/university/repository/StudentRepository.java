/*
 *
 * You can use the following import statements
 * 
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.university.repository;

import java.util.*;
import com.example.university.model.*;

public interface StudentRepository {

    ArrayList<Student> getAllStudents();

    Student getStudentById(int studentId);

    Student addStudent(Student student);

    Student updateStudent(int studentId, Student student);

    void deleteStudent(int studentId);

    ArrayList<Course> getCourses(int studentId);

}