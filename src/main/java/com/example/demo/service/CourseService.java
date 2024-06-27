package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.CourseFile;
import com.example.demo.repository.CourseFileRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.nio.file.Paths;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseFileRepository courseFileRepository;

    @Autowired
    private FileSystemStorageService storageService;


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }
    
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Course updateCourse(Long id, Course course) {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        if (existingCourse != null) {
            existingCourse.setCode(course.getCode());
            existingCourse.setTitle(course.getTitle());
            existingCourse.setInstructor(course.getInstructor());
            existingCourse.setSeason(course.getSeason());
            existingCourse.setYear(course.getYear());
            existingCourse.setStatus(course.getStatus());
            existingCourse.setInstructorName(course.getInstructorName());
            existingCourse.setInstructor(course.getInstructor());
            return courseRepository.save(existingCourse);
        }
        return null;
    }

    // @Transactional
    public CourseFile addFileToCourse(Long courseId, MultipartFile file) {
        // Store the file
        storageService.store(file);

        // Create a new CourseFile entity
        CourseFile courseFile = new CourseFile();
        courseFile.setFileName(file.getOriginalFilename());
        courseFile.setFilePath(
                Paths.get(storageService.getRootLocation().toString(), file.getOriginalFilename()).toString());

                
        // Find the course by ID
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new RuntimeException("Course not found");
        }
        Course course = courseOptional.get();

        courseFile.setCourse(course);

        List<CourseFile> files = course.getFiles();
        files.add(courseFile);
        course.setFiles(files);

        return courseFileRepository.save(courseFile);
    }

    // @Transactional
    public List<CourseFile> getCourseFiles(Long courseId) {
        // Find the course by ID
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new RuntimeException("Course not found");
        }
        Course course = courseOptional.get();

        // Return the list of files associated with the course
        // return courseFileRepository.findByCourse(course);
        // Log the course details
        System.out.println("Course: " + course.getTitle());
        for (CourseFile file : course.getFiles()) {
            System.out.println("File: " + file.getFileName());
        }
        return course.getFiles();

    }

    public void deleteCourseFile(Long courseId, Long fileId) {
        // Find the course by ID
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new RuntimeException("Course not found");
        }
        Course course = courseOptional.get();

        // Find the file by ID
        Optional<CourseFile> courseFileOptional = courseFileRepository.findById(fileId);
        if (!courseFileOptional.isPresent()) {
            throw new RuntimeException("File not found");
        }
        CourseFile courseFile = courseFileOptional.get();

        // Remove the file from the course
        course.getFiles().remove(courseFile);
        courseRepository.save(course);

        // Delete the file from the database
        courseFileRepository.deleteById(fileId);
    }
    


    
}
