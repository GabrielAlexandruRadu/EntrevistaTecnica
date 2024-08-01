package com.example.taskmanager;

import io.smallrye.mutiny.Uni;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    EntityManager entityManager;
    @Mock
    TypedQuery<Task> query;

    @InjectMocks
    TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("Task description");

        taskService.createTask(task).await().indefinitely();

        verify(entityManager).persist(task);
    }

    @Test
    public void testGetAllTasks() {
        Task task = new Task();
        task.setTitle("Task Title");
        task.setDescription("Task description");

        when(entityManager.createQuery("SELECT t FROM Task t", Task.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(task));

        List<Task> tasks = taskService.getAllTasks().await().indefinitely();

        assertEquals(1, tasks.size());
        assertEquals("Task Title", tasks.get(0).getTitle());
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);

        when(entityManager.find(Task.class, 1L)).thenReturn(task);

        Task result = taskService.getTaskById(1L).await().indefinitely();

        assertEquals(1L, result.getId());
        verify(entityManager).find(Task.class, 1L);
    }

    @Test
    public void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Existing Task");

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");

        when(entityManager.find(Task.class, 1L)).thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTask).await().indefinitely();

        assertEquals("Updated Task", result.getTitle());
        verify(entityManager).merge(existingTask);
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task();
        task.setId(1L);

        when(entityManager.find(Task.class, 1L)).thenReturn(task);

        taskService.deleteTask(1L).await().indefinitely();

        verify(entityManager).remove(task);
    }
}
