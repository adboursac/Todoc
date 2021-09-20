package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {
    private final ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    public LiveData<List<Project>> fetchAllProjects() {
        return mProjectDao.fetchAllProjects();
    }

    public void insert(Project project) {
        mProjectDao.insert(project);
    }

    public void delete(Project project) {
        mProjectDao.delete(project.getId());
    }
}