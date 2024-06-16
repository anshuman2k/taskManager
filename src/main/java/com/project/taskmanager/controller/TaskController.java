package com.project.taskmanager.controller;

import com.project.taskmanager.entity.Task;
import com.project.taskmanager.entity.User;
import com.project.taskmanager.repository.TaskRepository;
import com.project.taskmanager.repository.UserRepository;
import com.project.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("username")
@RequestMapping("/taskManager")
public class TaskController {
    @Autowired
    TaskService taskService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/addTask")
    public String addTask(Model model){

        if (model.containsAttribute("username")) {
            String username = (String) model.getAttribute("username");
            // Use the username as needed
            model.addAttribute("username", username);
            return "addTask";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/saveTask")
    public String saveTask(@RequestParam(name = "title") String title,
                           Model model){
        if (model.containsAttribute("username")) {
            String username = (String) model.getAttribute("username");
            User user = userRepository.findByUsername(username);
            taskService.saveTask(title, user);
            model.addAttribute("username", username);
            // Redirect to login page or secure area after successful signup
            return "taskManager";
        } else {
            return "redirect:/login";
        }
    }


    @GetMapping("/showTasks")
    public String showTask(Model model){
            // Use the username as needed
        if (model.containsAttribute("username")) {
            String username = (String) model.getAttribute("username");
            User user = userRepository.findByUsername(username);
            List<Task> tasks = taskRepository.findAllByUserId(user.getId());
            model.addAttribute("username", username);
            model.addAttribute("tasks", tasks);
            return "showTasks";
        }
        else {
            return "redirect:/login";
        }
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam(name = "taskId") Long taskId,
                             @RequestParam(name = "username") String username) {
        // Delete the task for the user
        taskService.deleteTask(taskId);
        // Redirect back to the task list
        return "redirect:/taskManager/showTasks?username=" + username;
    }

    @PostMapping("/markTaskDone")
    public String markTaskDone(@RequestParam(name = "taskId") Long taskId,
                               @RequestParam(name = "username") String username) {
        // Mark the task as done
        taskService.markTaskDone(taskId);
        // Redirect back to the task list
        return "redirect:/taskManager/showTasks?username=" + username;
    }
}
