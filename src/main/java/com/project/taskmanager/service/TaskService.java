package com.project.taskmanager.service;

import com.project.taskmanager.entity.Task;
import com.project.taskmanager.entity.User;
import com.project.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService{

  @Autowired
  private TaskRepository taskRepository;

  public List<Task> getUserTasks(Long userId) {
    // Implement logic to retrieve tasks for the user based on userId
    // This might involve fetching from a database using the userId
    return taskRepository.findAllByUserId(userId);
  }

  public Task addTask(Task task) {
    // Set the user for the task
    task.setUser(task.getUser());
    // Save the task to the data source
    return taskRepository.save(task);
  }

  public void saveTask(String title, User user){
    Task task = new Task();
    task.setTitle(title);
    task.setUser(user);
    taskRepository.save(task);
  }

  public void deleteTask(Long taskId) {
    // Find the task by ID
    Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
    // Delete the task from the data source (e.g., database)
    taskRepository.delete(task);
  }

  public Task markTaskDone(Long taskId) {
    // Find the task by ID
    Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
    // Update the task's "done" flag to true
    task.setCompleted(true);
    // Save the updated task to the data source (e.g., database)
    return taskRepository.save(task);
  }
}
