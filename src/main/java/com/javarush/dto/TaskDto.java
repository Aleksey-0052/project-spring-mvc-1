package com.javarush.dto;

import com.javarush.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDto {

    @NotBlank(message = "Описание не может быть пустым или состоять только из пробелов")
    @Size(max = 100, message = "Описание может включать не более 100 символов")
    private String description;

    @NotBlank(message = "Статус не может быть null")
    private Status status;

}
