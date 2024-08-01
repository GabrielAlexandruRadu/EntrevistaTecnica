package com.example.taskmanager;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TaskService {
    @Inject
    EntityManager entityManager;

    @Transactional
    public Uni<Void> createTask(Task task) {
        return Uni.createFrom().voidItem()
                .invoke(() -> entityManager.persist(task));
    }

    public Uni<List<Task>> getAllTasks() {
        return Uni.createFrom().item(() ->
                entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList()
        );
    }

    public Uni<Task> getTaskById(Long id) {
        return Uni.createFrom().item(() ->
                Optional.ofNullable(entityManager.find(Task.class, id))
                        .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"))
        );
    }

    @Transactional
    public Uni<Task> updateTask(Long id, Task updatedTask) {
        return Uni.createFrom().item(() -> getTaskById(id).await().indefinitely())
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setDueDate(updatedTask.getDueDate());
                    entityManager.merge(task);
                    return task;
                });
    }

    @Transactional
    public Uni<Void> deleteTask(Long id) {
        return Uni.createFrom().item(() -> getTaskById(id).await().indefinitely())
                .invoke(entityManager::remove)
                .replaceWithVoid();
    }
}
