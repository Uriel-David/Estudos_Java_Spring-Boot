package com.uriel.apispring.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.uriel.apispring.dto.CourseDTO;
import com.uriel.apispring.dto.CourseRequestDTO;
import com.uriel.apispring.dto.LessonDTO;
import com.uriel.apispring.models.Course;
import com.uriel.apispring.models.Lesson;

@Component
public class CourseMapper {
    public Course toModel(CourseRequestDTO courseRequestDTO) {

        Course course = Course.builder().name(courseRequestDTO.name()).category(courseRequestDTO.category()).build();

        Set<Lesson> lessons = courseRequestDTO.lessons().stream()
                .map(lessonDTO -> Lesson.builder().id(lessonDTO._id()).name(lessonDTO.name())
                        .youtubeUrl(lessonDTO.youtubeUrl()).course(course).build())
                .collect(Collectors.toSet());
        course.setLessons(lessons);
        return course;
    }

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }
        List<LessonDTO> lessonDTOList = course.getLessons()
                .stream()
                .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
                .collect(Collectors.toList());
        return new CourseDTO(course.getId(), course.getName(), course.getCategory(), lessonDTOList);
    }
}
