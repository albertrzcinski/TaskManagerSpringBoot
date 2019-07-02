package isi.taskmanager.db;

import isi.taskmanager.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    TaskModel findByTitle(String title);
}
