package com.javarush.mapper;

import com.javarush.domain.Task;
import com.javarush.dto.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskDTO taskDto);

    @Mapping(target = "id", ignore = true)
    void updateTaskFromTaskDto(TaskDTO taskDto, @MappingTarget Task task);

}
