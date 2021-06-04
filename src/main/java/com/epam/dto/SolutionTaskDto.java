package com.epam.dto;

import com.epam.entities.SolutionStatus;

public class SolutionTaskDto {
    private Long solutionId;
    private Long taskId;
    private Long userId;
    private SolutionStatus solutionStatus;
    private String solutionValue;
    private Integer solutionMark;
    private String solutionComment;
    private String taskName;
    private String taskDescription;

    public SolutionTaskDto(Long solutionId, Long taskId, Long userId, SolutionStatus solutionStatus,
                           String solutionValue, Integer solutionMark, String solutionComment, String taskName, String taskDescription) {
        this.solutionId = solutionId;
        this.taskId = taskId;
        this.userId = userId;
        this.solutionStatus = solutionStatus;
        this.solutionValue = solutionValue;
        this.solutionMark = solutionMark;
        this.solutionComment = solutionComment;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public SolutionTaskDto() {

    }

    public Long getSolutionId() {
        return solutionId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public SolutionStatus getSolutionStatus() {
        return solutionStatus;
    }

    public String getSolutionValue() {
        return solutionValue;
    }

    public Integer getSolutionMark() {
        return solutionMark;
    }

    public String getSolutionComment() {
        return solutionComment;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setSolutionId(Long solutionId) {
        this.solutionId = solutionId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setSolutionStatus(SolutionStatus solutionStatus) {
        this.solutionStatus = solutionStatus;
    }

    public void setSolutionValue(String solutionValue) {
        this.solutionValue = solutionValue;
    }

    public void setSolutionMark(Integer solutionMark) {
        this.solutionMark = solutionMark;
    }

    public void setSolutionComment(String solutionComment) {
        this.solutionComment = solutionComment;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
