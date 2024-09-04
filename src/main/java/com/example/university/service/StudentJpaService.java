/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.university.model.*;
import com.example.university.repository.*;

@Service
public class StudentJpaService implements StudentRepository {

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Override
    public ArrayList<Student> getAllStudents() {
        List<Student> studentList = studentJpaRepository.findAll();
        ArrayList<Student> students = new ArrayList<>(studentList);
        return students;
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            return student;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(Student student) {
        try {
            List<Course> courses = student.getCourses();
            ArrayList<Integer> courseIds = new ArrayList<>();
            for (Course course : courses) {
                courseIds.add(course.getCourseId());
            }

            List<Course> compCourses = courseJpaRepository.findAllById(courseIds);
            if (compCourses.size() != courseIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            for (Course course : compCourses) {
                course.getStudents().add(student);
            }
            courseJpaRepository.saveAll(compCourses);
            student.setCourses(compCourses);
            Student saveStudent = studentJpaRepository.save(student);
            return saveStudent;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        try {
            Student newStudent = studentJpaRepository.findById(studentId).get();
            if (student.getStudentName() != null) {
                newStudent.setStudentName(student.getStudentName());
            }
            if (student.getEmail() != null) {
                newStudent.setEmail(student.getEmail());
            }
            if (student.getCourses() != null) {
                List<Course> courses = newStudent.getCourses();
                for (Course course : courses) {
                    course.getStudents().remove(newStudent);
                }
                courseJpaRepository.saveAll(courses);
                List<Integer> courseIds = new ArrayList<>();
                for (Course course : student.getCourses()) {
                    courseIds.add(course.getCourseId());
                }
                List<Course> compCourses = courseJpaRepository.findAllById(courseIds);
                if (compCourses.size() != courseIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                for (Course course : compCourses) {
                    course.getStudents().add(newStudent);
                }
                courseJpaRepository.saveAll(compCourses);
                newStudent.setCourses(compCourses);
            }
            studentJpaRepository.save(newStudent);
            return newStudent;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteStudent(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            List<Course> courses = student.getCourses();
            for (Course course : courses) {
                course.getStudents().remove(student);
            }
            courseJpaRepository.saveAll(courses);
            studentJpaRepository.deleteById(studentId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public ArrayList<Course> getCourses(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            ArrayList<Course> result = new ArrayList<>(student.getCourses());
            return result;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}