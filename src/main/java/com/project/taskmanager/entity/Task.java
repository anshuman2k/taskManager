package com.project.taskmanager.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(columnDefinition = "boolean default false")
  private boolean completed;

  // Optional no-argument constructor
  public Task() {
  }

  public Task(String title, String description, boolean completed) {
    this.title = title;
    this.completed = completed;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setDone(boolean done) {
    this.completed = done;
  }
}