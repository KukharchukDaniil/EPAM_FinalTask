package com.epam.ftask.entities;

import java.util.Objects;

public class Task implements Entity{
    private Long id;
    private Long courseId;
    private String name;
    private String description;

    public Task() {
    }

    public Task(Long id, Long courseId, String name, String description) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public void setId(Long id) {
        this.id = id;
    }
}
