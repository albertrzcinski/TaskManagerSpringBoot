package isi.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String expLevel;

    private float salary = 0;
    private String hireFrom;
    private float timeForTask = 0;

    private float multiply = 0.0f;
    private float realCostPerHour = 0.0f;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private TaskModel taskModel;

    public EmployeeModel(){}

    public EmployeeModel(String username, String password, String firstName,
                         String lastName, String expLevel, TaskModel taskModel) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expLevel = expLevel;
        this.taskModel = taskModel;

        this.setMultiply();
    }

    public EmployeeModel(String username, String password, String firstName,
                         String lastName, String expLevel, float timeForTask, float salary, TaskModel taskModel) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expLevel = expLevel;
        this.taskModel = taskModel;
        this.timeForTask = timeForTask;
        this.salary = salary;

        this.setMultiply();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskModel getTaskModel() {
        return taskModel;
    }

    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(String expLevel) {
        this.expLevel = expLevel;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getHireFrom() {
        return hireFrom;
    }

    public void setHireFrom(String hireFrom) {
        this.hireFrom = hireFrom;
    }

    public float getTimeForTask() {
        return timeForTask;
    }

    public void setTimeForTask(float timeForTask) {
        this.timeForTask = timeForTask;
    }

    @JsonIgnore
    public TaskModel getTask() {
        return taskModel;
    }

    @JsonIgnore
    public void setTask(TaskModel taskModel) {
        this.taskModel = taskModel;
    }

    private void setMultiply(){
        switch (this.expLevel) {
            case "Junior":
                this.multiply = 1.0f;
                break;
            case "Mid":
                this.multiply = 1.25f;
                break;
            case "Senior":
                this.multiply = 1.5f;
                break;
        }
    }

    public float getMultiply() {
        return multiply;
    }

    public float getRealCostPerHour() {
        return realCostPerHour;
    }

    public void setRealCostPerHour(float realCost) {
        this.realCostPerHour = realCost;
    }
}
