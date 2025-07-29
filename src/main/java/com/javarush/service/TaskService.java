package com.javarush.service;

import com.javarush.dao.TaskDAO;
import com.javarush.domain.Task;
import com.javarush.dto.TaskDTO;
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
    private final TaskMapper taskMapper;


    public List<Task> getAll(int offset, int limit) {
        return taskDAO.getAll(offset, limit);
    }

    public int getAllCount() {
        return taskDAO.getAllCount();
    }

    @Transactional
    public Task edit(int id, TaskDTO taskDTO) {
        Task task = taskDAO.getById(id);
        if (Objects.isNull(task)) {
            throw new ResourceNotFoundException(id);
        }
        taskMapper.updateTaskFromTaskDto(taskDTO, task);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    public Task create(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = taskDAO.getById(id);
        if (isNull(task)) {
            throw new ResourceNotFoundException(id);
        }
        taskDAO.delete(task);
    }

}
