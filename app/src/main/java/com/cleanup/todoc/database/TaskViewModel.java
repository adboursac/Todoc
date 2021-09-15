package com.cleanup.todoc.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor mExecutor;

    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        mExecutor = executor;
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTaskRepository.getAllTasks();
    }

    public LiveData<List<Project>> getAllProjects() {
        return mProjectRepository.getAllProjects();
    }

    public void insertTask(Task task) {
        mExecutor.execute(() -> {
            mTaskRepository.insert(task);
        });
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> {
            mTaskRepository.delete(task);
        });
    }

    /**
     * Returns the project with the given unique identifier, or null if no project with that
     * identifier can be found.
     *
     * @param projectId the unique identifier of the project to return
     * @param projectList the current list of projects
     * @return the project with the given unique identifier, or null if it has not been found
     */
    public static Project getProjectById(long projectId, List<Project> projectList) {
        for (Project project : projectList) {
            if (project.getId() == projectId) return project;
        }
        return null;
    }
}
