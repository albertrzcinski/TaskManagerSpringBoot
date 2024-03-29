package isi.taskmanager.controller;

import isi.taskmanager.db.TaskRepository;
import isi.taskmanager.model.TaskModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("task")
@CrossOrigin
public class TaskController {

    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("all")
    public List<TaskModel> getAll() {
        return this.taskRepository.findAll();
    }

    @PostMapping("save")
    public List<TaskModel> save(@RequestBody TaskModel taskModel) {
        this.taskRepository.save(taskModel);

        return this.taskRepository.findAll();
    }

    @GetMapping("/byId/{id}")
    public Optional<TaskModel> getById(@PathVariable Long id){
        return this.taskRepository.findById(id);
    }
}
