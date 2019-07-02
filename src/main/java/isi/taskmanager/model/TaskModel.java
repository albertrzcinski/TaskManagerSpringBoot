package isi.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String start;

    @Column(nullable = false)
    private String end;

    @Column(nullable = false)
    private String client;

    @Column(nullable = false)
    private String description;

    private float time;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskModel", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EmployeeModel> employeeModel;

    public TaskModel(){}

    public TaskModel(String title, String start, String end, String client, String description, float time) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.client = client;
        this.description = description;
        this.time = time;
        this.employeeModel = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EmployeeModel> getEmployeeModel() {
        return employeeModel;
    }

    public void setEmployeeModel(List<EmployeeModel> employeeModel) {
        this.employeeModel = employeeModel;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
