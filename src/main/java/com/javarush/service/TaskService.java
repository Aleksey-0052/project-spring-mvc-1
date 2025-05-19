package com.javarush.service;

import com.javarush.dao.TaskDAO;
import com.javarush.domain.Task;
import com.javarush.dto.TaskDto;
import com.javarush.exception.ResourceNotFoundException;
import com.javarush.mapper.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskDAO taskDAO;
    private final TaskMapper mapper;


    public List<Task> getAll(int offset, int limit) {
        return taskDAO.getAll(offset, limit);
    }

    public int getAllCount() {
        return taskDAO.getAllCount();
    }

    @Transactional
    public Task edit(int id, TaskDto taskDto) {
        Task task = taskDAO.getById(id);
        if (Objects.isNull(task)) {
            throw new ResourceNotFoundException("Entity not found");
        }
        mapper.updateTaskFromTaskDto(taskDto, task);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    public Task create(TaskDto taskDto) {
        Task task = mapper.toEntity(taskDto);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = taskDAO.getById(id);
        if (isNull(task)) {
            throw new ResourceNotFoundException("Entity not found");
        }
        taskDAO.delete(task);
    }

}
