package isi.taskmanager;

import isi.taskmanager.controller.EmployeeController;
import isi.taskmanager.db.EmployeeRepository;
import isi.taskmanager.db.TaskRepository;
import isi.taskmanager.model.AuthProvider;
import isi.taskmanager.model.EmployeeModel;
import isi.taskmanager.model.TaskModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

public class EmployeeControllerTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private EmployeeController employeeController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        TaskModel taskModel = new TaskModel("Default", "","","","",0);
        taskModel.setId(1L);
        ArrayList<TaskModel> taskModels = new ArrayList<>();
        taskModels.add(taskModel);
        EmployeeModel employeeModel = new EmployeeModel("empl1", "e1", "l3rqeq2jag@ss.rw",
                "Junior",2, 170, taskModels.get(0), AuthProvider.local);
        employeeModel.setId(2L);
        ArrayList<EmployeeModel> employeeModels = new ArrayList<>();
        employeeModels.add(employeeModel);

        when(employeeRepository.findAll()).thenReturn(employeeModels);
        when(taskRepository.findByTitle("Default")).thenReturn(taskModel);

        //Tylko jeden pracownik na liście przypisany do zadania "Default"
        when(employeeRepository.findAllByTaskModel(taskModel)).thenReturn(employeeModels);
    }

    @Test
    public void testGetAll() {
        //Wywołaj testowaną metodę
        List<EmployeeModel> result = employeeController.getAll();

        //Sprawdź czy faktycznie kontroler wywoła odpowiednią metodę
        verify(employeeRepository).findAll();

        assertThat("Wynikowa lista nie może być null", result, is(notNullValue()));
        assertThat("Powinna mieć dokładnie jeden element", result.size(), is(1));
        assertThat("Id musi być 2", result.get(0).getId(), is(2L));
    }

    @Test
    public void testSave() {
        EmployeeModel employeeModel = new EmployeeModel();
        //Wynik save bez znaczenia
        when(employeeRepository.save(employeeModel)).thenReturn(null);

        List<EmployeeModel> result = employeeController.save(employeeModel);

        InOrder inOrder = Mockito.inOrder(employeeRepository);

        //Kolejność ma znaczenie
        inOrder.verify(employeeRepository).save(employeeModel);
        inOrder.verify(employeeRepository).findAll();

        assertThat("Wynikowa lista nie może być null", result, is(notNullValue()));
        assertThat("Powinna mieć dokładnie jeden element", result.size(), is(1));
        assertThat("Id musi być 2", result.get(0).getId(), is(2L));
    }

    @Test
    public void testGetByTaskGivenEmployeesAssigned() {
        List<EmployeeModel> result = employeeController.getByTask("Default");

        verify(taskRepository).findByTitle("Default");

        assertThat("Wynikowa lista nie może być null", result, is(notNullValue()));
        assertThat("Powinna mieć dokładnie jeden element", result.size(), is(1));
        assertThat("Id musi być 2", result.get(0).getId(), is(2L));
    }

    @Test
    public void testGetByTaskGivenNoEmployeesAssigned(){
        //Mock inne zadanie bez przypisanych pracowników
        TaskModel otherTask = new TaskModel();
        otherTask.setTitle("Inny tytul");
        otherTask.setId(3L);

        when(taskRepository.findByTitle("Inny tytul")).thenReturn(otherTask);
        //Tu nie ma listy
        when(employeeRepository.findAllByTaskModel(otherTask)).thenReturn(null);

        List<EmployeeModel> result = employeeController.getByTask("Inny tytul");

        verify(taskRepository).findByTitle("Inny tytul");

        assertThat("Powinno zwrócić null - brak pracowników", result, is(nullValue()));
    }

    @Test
    public void testGetEmployeeToTask() {
        TaskModel taskModel = new TaskModel("Game", "2019-08-19", "2019-08-20", "CDP Blue",
                "Test", 1.0f);
        //Wywołaj testowaną metodę
        List<EmployeeModel> result = employeeController.getEmployeeToTask(taskModel);

        verify(taskRepository).findByTitle("Default");

        assertThat("Wynikowa lista nie może być null", result, is(notNullValue()));
        //Tylko jeden dostępny pracownik (z TimeForTask = 2) więc powinno go przydzielić
        assertThat("Powinna mieć dokładnie jeden element", result.size(), is(1));
        assertThat("Id musi być 2", result.get(0).getId(), is(2L));
    }
}
