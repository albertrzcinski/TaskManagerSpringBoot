package isi.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String password;

    @Column(nullable = false)
    @Email
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    private String expLevel;
    private float salary = 0;
    private String hireFrom;
    private float timeForTask = 0;

    private float multiply = 0.0f;
    private float realCostPerHour = 0.0f;

    private boolean isAdmin = false;
    private boolean isFreeVersion = true;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private TaskModel taskModel;

    public EmployeeModel(){}

    public EmployeeModel(String name, String password, String email,
                          TaskModel taskModel, AuthProvider provider) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.taskModel = taskModel;
        this.provider = provider;

        this.setMultiply();
    }

    public EmployeeModel(String name, String password, String email,
                         String expLevel, float timeForTask, float salary, TaskModel taskModel, AuthProvider provider) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.expLevel = expLevel;
        this.taskModel = taskModel;
        this.timeForTask = timeForTask;
        this.salary = salary;
        this.provider = provider;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (this.expLevel!=null) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isFreeVersion() {
        return isFreeVersion;
    }

    public void setFreeVersion(boolean freeVersion) {
        isFreeVersion = freeVersion;
    }
}
