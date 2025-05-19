package com.javarush.controller;

import com.javarush.domain.Task;
import com.javarush.dto.TaskDto;
import com.javarush.exception.InvalidIdException;
import com.javarush.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @GetMapping(value = "/")
    public String tasks(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) {
        List<Task> tasks = taskService.getAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "tasks";
    }


    @PostMapping("/{id}")
    public String edit(@PathVariable Integer id,
                       @Valid @RequestBody TaskDto taskDto,
                       BindingResult bindingResult,
                       Model model) {
        validateId(id);
        Task task = taskService.edit(id, taskDto);
        return tasks(model, 1, 10);
    }


    @PostMapping("/")
    public String add(@Valid @RequestBody TaskDto taskDto, BindingResult bindingResult, Model model) {
        Task task = taskService.create(taskDto);
        return tasks(model, 1, 10);
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        validateId(id);
        taskService.delete(id);
        return tasks(model, 1, 10);
    }


    private void validateId(Integer id) {
        if (Objects.isNull(id) || id <= 0) {
            throw new InvalidIdException("Invalid task id");
        }
    }

}
