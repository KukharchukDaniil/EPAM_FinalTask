package com.epam.entities;

import java.util.Objects;

public class Solution implements Entity {
    private Long id;
    private Long taskId;
    private Long userId;
    private SolutionStatus status;
    private String value;
    private Integer mark;
    private String comment;

    public Solution() {
    }

    public Solution(Long id, Long taskId, Long userId, SolutionStatus status, String value, Integer mark, String comment) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
        this.status = status;
        this.value = value;
        this.mark = mark;
        this.comment = comment;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public SolutionStatus getStatus() {
        return status;
    }

    public Integer getMark() {
        return mark;
    }

    public String getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return Objects.equals(id, solution.id) && Objects.equals(taskId, solution.taskId) &&
                Objects.equals(userId, solution.userId) && status == solution.status &&
                Objects.equals(value, solution.value) && Objects.equals(mark, solution.mark)
                && Objects.equals(comment, solution.comment);
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskId, userId, status, value, mark, comment);
    }
}
