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
public class CourseJpaService implements CourseRepository {

    @Autowired
    private CourseJpaRepository courseJpaRepository;
    @Autowired
    private StudentJpaRepository studentJpaRepository;
    @Autowired
    private ProfessorJpaRepository professorJpaRepository;

    @Override
    public ArrayList<Course> getAllCourses() {
        List<Course> courseList = courseJpaRepository.findAll();
        ArrayList<Course> courses = new ArrayList<>(courseList);
        return courses;
    }

    @Override
    public Course getCourseById(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return course;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Course addCourse(Course course) {
        Professor professor = course.getProfessor();
        int professorId = professor.getProfessorId();

        try {
            Professor completeProfessor = professorJpaRepository.findById(professorId).get();
            course.setProfessor(completeProfessor);
            List<Integer> studentIds = new ArrayList<>();
            for (Student student : course.getStudents()) {
                studentIds.add(student.getStudentId());
            }
            List<Student> completeStudents = studentJpaRepository.findAllById(studentIds);
            if (completeStudents.size() != studentIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            for (Student student : completeStudents) {
                student.getCourses().add(course);
            }
            course.setStudents(completeStudents);
            Course saveCourse = courseJpaRepository.save(course);
            studentJpaRepository.saveAll(completeStudents);
            return saveCourse;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Course updateCourse(int courseId, Course course) {
        try {
            Course newCourse = courseJpaRepository.findById(courseId).get();
            if (course.getCourseName() != null) {
                newCourse.setCourseName(course.getCourseName());
            }
            if (course.getCredits() != null) {
                newCourse.setCredits(course.getCredits());
            }
            if (course.getProfessor() != null) {
                Professor professor = course.getProfessor();
                int professorId = professor.getProfessorId();
                Professor completProfessor = professorJpaRepository.findById(professorId).get();
                course.setProfessor(completProfessor);
            }
            if (course.getStudents() != null) {
                List<Student> students = newCourse.getStudents();
                for (Student student : students) {
                    student.getCourses().remove(newCourse);
                }
                studentJpaRepository.saveAll(students);
                List<Integer> newStudentIds = new ArrayList<>();
                for (Student student : course.getStudents()) {
                    newStudentIds.add(student.getStudentId());
                }
                List<Student> newStudents = studentJpaRepository.findAllById(newStudentIds);
                if (newStudents.size() != newStudentIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                for (Student student : newStudents) {
                    student.getCourses().add(newCourse);
                }
                studentJpaRepository.saveAll(newStudents);
                newCourse.setStudents(newStudents);
            }
            courseJpaRepository.save(newCourse);
            return newCourse;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void deleteCourse(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            List<Student> students = course.getStudents();
            for (Student student : students) {
                student.getCourses().remove(course);
            }
            studentJpaRepository.saveAll(students);
            courseJpaRepository.deleteById(courseId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Professor getProfessor(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return course.getProfessor();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ArrayList<Student> getStudents(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            ArrayList<Student> result = new ArrayList<>(course.getStudents());
            return result;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}