package com.example.demo.model;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String title;

    @OneToMany
    private List<CourseFile> files;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = true)
    @JsonBackReference
    private Instructor instructor;
    
    // add coiumn for instructor name
    @Column(name = "instructor_name")
    private String instructorName;

    private String season;

    @Column(name = "theYear")

    private String year;

    private String status;

    public Course() {
    }

    public Course(String code, String title, Instructor instructor, String season, String year, String status , String instructor_name ) {
        this.code = code;
        this.title = title;
        this.instructor = instructor;
        this.season = season;
        this.year = year;
        this.status = status;
        this.instructorName = instructor_name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<CourseFile> getFiles() {
        return files;
    }

    public void setFiles(List<CourseFile> files) {
        this.files = files;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // get instructor
    public Long getInstructorId() {
        return instructor != null ? instructor.getId() : null;
    }

    // set instructor
    public void setInstructorId(Long instructor_id) {
        if (instructor == null) {
            instructor = new Instructor();
        }
        instructor.setId(instructor_id);
    }

    //get instructor name
    public String getInstructorName() {
        return instructor != null ? instructor.getName() : null;
    }

    //set instructor name
    public void setInstructorName(String instructor_name) {
        this.instructorName = instructor_name;
    }

}