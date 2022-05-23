package ru.otus.vacancykeeper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.vacancykeeper.domain.Task;
import ru.otus.vacancykeeper.search.TaskSearchValues;
import ru.otus.vacancykeeper.service.TaskService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/all")
    public List<Task> findAll() {
        return taskService.findTasksOrderByTitle();
    }

    @GetMapping("id/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(taskService.findById(id));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("Task id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<Task>> search(@RequestBody TaskSearchValues taskSearchValues) {
        return ResponseEntity.ok(taskService.findTasksByTitle(taskSearchValues.getTitle()));
    }

}
