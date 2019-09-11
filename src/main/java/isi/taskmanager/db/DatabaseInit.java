package isi.taskmanager.db;

import isi.taskmanager.model.AuthProvider;
import isi.taskmanager.model.EmployeeModel;
import isi.taskmanager.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "dbrecreate", havingValue = "true")
public class DatabaseInit implements CommandLineRunner {

    private EmployeeRepository employeeRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public DatabaseInit(EmployeeRepository employeeRepository, TaskRepository taskRepository,
                        UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        employeeRepository.deleteAll();
        taskRepository.deleteAll();


        List<TaskModel> taskModelList = new ArrayList<>();

        taskModelList.add(new TaskModel("Game", "2019-08-19", "2019-08-22", "CDP Red",
                "Make a game for enthusiastic clients. Good luck, have fun :)", 4.0f));
        taskModelList.add(new TaskModel("Default", "","","","",0));

        taskRepository.saveAll(taskModelList);

        List<EmployeeModel> employeeModelList = new ArrayList<>();

        employeeModelList.add(new EmployeeModel("empl2", "e2", "lfahljag@ss.rw",
                taskModelList.get(1), AuthProvider.local));

        employeeModelList.add(new EmployeeModel("empl1", "e1", "l3rqeq2jag@ss.rw",
                "Junior",3, 170, taskModelList.get(1),AuthProvider.local));

        employeeModelList.add(new EmployeeModel("empll3", "e4", "rt123hljag@ss.rw",
                "Mid", 2, 340, taskModelList.get(1), AuthProvider.local));

        employeeModelList.add(new EmployeeModel("username","password","ah21jag@ss.a2",
                taskModelList.get(0), AuthProvider.local));

        employeeRepository.saveAll(employeeModelList);
    }
}
