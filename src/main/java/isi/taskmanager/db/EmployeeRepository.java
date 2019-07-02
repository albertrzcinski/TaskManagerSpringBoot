package isi.taskmanager.db;

import isi.taskmanager.model.EmployeeModel;
import isi.taskmanager.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {
    List<EmployeeModel> findAllByTaskModel (TaskModel taskModel);
}
