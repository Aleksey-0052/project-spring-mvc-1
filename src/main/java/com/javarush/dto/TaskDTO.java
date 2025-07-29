package com.javarush.dto;

import com.javarush.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

    @NotBlank(message = "Описание не может быть пустым или состоять только из пробелов")
    @Size(min = 5, max = 10, message = "Описание может включать не менее 5 и не более 10 символов")
    private String description;

    private Status status;

}
