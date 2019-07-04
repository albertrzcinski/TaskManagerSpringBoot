package isi.taskmanager;

import isi.taskmanager.controller.TaskController;
import isi.taskmanager.db.TaskRepository;
import isi.taskmanager.model.TaskModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskRepository taskRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        //Utwórz obiekt który powinno zwrócić
        TaskModel taskModel = new TaskModel("Default", "", "", "", "", 0);
        taskModel.setId(1L);
        ArrayList<TaskModel> returnedList = new ArrayList<>();
        returnedList.add(taskModel);

        //Mock
        when(taskRepository.findAll()).thenReturn(returnedList);

    }

    @Test
    public void testGetAll() {
        //Wywołaj testowaną metodę
        List<TaskModel> result = taskController.getAll();

        //Sprawdź czy faktycznie kontroler wywoła odpowiednią metodę
        verify(taskRepository).findAll();

        assertThat("Wynikowa lista nie może być null", result, is(notNullValue()));
        assertThat("Powinna mieć dokładnie jeden element", result.size(), is(1));
        assertThat("Id musi być 1", result.get(0).getId(), is(1L));
    }

    @Test
    public void testSave(){
        //Cokolwiek bez znaczenia
        TaskModel taskModel = new TaskModel();
        //Tu nie ma znaczenie właściwie co repo zwróci
        when(taskRepository.save(taskModel)).thenReturn(null);

        List<TaskModel> result = taskController.save(taskModel);

        InOrder inOrder = Mockito.inOrder(taskRepository);

        //Powinno uruchomić tą metodę z pustym parametrem
        inOrder.verify(taskRepository).save(taskModel);
        //A później tą
        inOrder.verify(taskRepository).findAll();

        //Te same oczekiwane wyniki co dla testGetAll()
        assertThat("Wynikowa lista nie może być null", result, is(notNullValue()));
        assertThat("Powinna mieć dokładnie jeden element", result.size(), is(1));
        assertThat("Id musi być 1", result.get(0).getId(), is(1L));
    }
}
