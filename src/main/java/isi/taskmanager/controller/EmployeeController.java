package isi.taskmanager.controller;

import isi.taskmanager.db.EmployeeRepository;
import isi.taskmanager.db.TaskRepository;
import isi.taskmanager.exception.ResourceNotFoundException;
import isi.taskmanager.model.EmployeeModel;
import isi.taskmanager.model.TaskModel;
import isi.taskmanager.security.CurrentUser;
import isi.taskmanager.security.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    private EmployeeRepository employeeRepository;
    private TaskRepository taskRepository;

    public EmployeeController(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public EmployeeModel getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return employeeRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<EmployeeModel> getAll() {
        return this.employeeRepository.findAll();
    }

    @PostMapping("save")
    public List<EmployeeModel> save(@RequestBody EmployeeModel employeeModel) {
        this.employeeRepository.save(employeeModel);

        return this.employeeRepository.findAll();
    }

    @GetMapping("/byId/{id}")
    public Optional<EmployeeModel> getById(@PathVariable Long id){
        return this.employeeRepository.findById(id);
    }

    @GetMapping("/byTask/{title}")
    public List<EmployeeModel> getByTask(@PathVariable String title) {

        TaskModel taskModel = this.taskRepository.findByTitle(title);

        return this.employeeRepository.findAllByTaskModel(taskModel);
    }

    @GetMapping("toTask")
    public List<EmployeeModel> getEmployeeToTask(@RequestBody TaskModel taskModel) {

        TaskModel defaultTask = this.taskRepository.findByTitle("Default");

        List<EmployeeModel> availableEmployeeList = this.employeeRepository.findAllByTaskModel(defaultTask);

        if(!availableEmployeeList.isEmpty()) {

            Iterator<EmployeeModel> iterator = availableEmployeeList.iterator();
            float entireEmployeeTime = 0.0f;

            while (iterator.hasNext()) {
                EmployeeModel e = iterator.next();
                if(e.getTimeForTask() > 0)
                    entireEmployeeTime += (e.getTimeForTask()*e.getMultiply());
                else
                    iterator.remove();
            }

            float taskTime = taskModel.getTime();

            if (entireEmployeeTime >= taskTime) {
                for (EmployeeModel e: availableEmployeeList) {
                    e.setRealCostPerHour((e.getTimeForTask() * e.getSalary())/(e.getTimeForTask()*e.getMultiply()));
                }

                availableEmployeeList.sort(Comparator.comparing(EmployeeModel::getRealCostPerHour));

                iterator = availableEmployeeList.iterator();

                List<EmployeeModel> removeEmployeeList  = new ArrayList<>();

                while (iterator.hasNext()){
                    EmployeeModel e = iterator.next();

                    if(taskTime <= 0)
                        removeEmployeeList.add(e);
                    else {
                        if (taskTime < 1) taskTime = 1;

                        if (e.getTimeForTask() > taskTime) {
                            e.setTimeForTask(taskTime);
                            taskTime = -1;
                        }
                        else
                            taskTime = taskTime - (e.getTimeForTask() * e.getMultiply());
                    }
                }

                availableEmployeeList.removeAll(removeEmployeeList);

                //If you want to save result to db
                employeeRepository.saveAll(availableEmployeeList);

                return availableEmployeeList;
            }
            else
                return null;
        }
        else
            return null;
    }
}
