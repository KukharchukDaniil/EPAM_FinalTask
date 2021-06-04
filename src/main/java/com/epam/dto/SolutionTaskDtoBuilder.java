package com.epam.dto;

import com.epam.entities.SolutionStatus;

public class SolutionTaskDtoBuilder{
    SolutionTaskDto result = new SolutionTaskDto();
    public SolutionTaskDtoBuilder setTaskId(Long taskId) {
        result.setTaskId(taskId);
        return this;
    }

    
    public SolutionTaskDtoBuilder setUserId(Long userId) {
        result.setUserId(userId);
        return this;
    }

    
    public SolutionTaskDtoBuilder setStatus(SolutionStatus status) {
        result.setSolutionStatus(status);
        return this;
    }

    
    public SolutionTaskDtoBuilder setValue(String value) {
        result.setSolutionValue(value);
        return this;
    }

    
    public SolutionTaskDtoBuilder setMark(Integer mark) {
        result.setSolutionMark(mark);
        return this;
    }

    
    public SolutionTaskDtoBuilder setComment(String comment) {
        result.setSolutionComment(comment);
        return this;
    }

    
    public SolutionTaskDtoBuilder setTaskName(String taskName) {
        result.setTaskName(taskName);
        return this;
    }

    
    public SolutionTaskDtoBuilder setTaskDescription(String taskDescription) {
        result.setTaskDescription(taskDescription);
        return this;
    }
    public SolutionTaskDtoBuilder setSolutionId(Long solutionId) {
        result.setSolutionId(solutionId);
        return this;
    }
    
    public SolutionTaskDto build() {
        return result;
    }
}
