package com.javarush.mapper;

import com.javarush.domain.Task;
import com.javarush.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskDto taskDto);

    @Mapping(target = "id", ignore = true)
    void updateTaskFromTaskDto(TaskDto taskDto, @MappingTarget Task task);

}
