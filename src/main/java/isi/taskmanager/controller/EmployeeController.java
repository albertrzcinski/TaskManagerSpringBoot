package isi.taskmanager.controller;

import isi.taskmanager.db.EmployeeRepository;
import isi.taskmanager.db.TaskRepository;
import isi.taskmanager.model.EmployeeModel;
import isi.taskmanager.model.TaskModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<EmployeeModel> getAll() {
        return this.employeeRepository.findAll();
    }

    @PostMapping("save")
    public List<EmployeeModel> save(@RequestBody EmployeeModel employeeModel) {
        this.employeeRepository.save(employeeModel);

        return this.employeeRepository.findAll();
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
                return availableEmployeeList;
            }
            else
                return null;
        }
        else
            return null;
    }
}
