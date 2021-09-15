package com.cleanup.todoc.database;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {
    private final ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    LiveData<List<Project>> getAllProjects() {
        return mProjectDao.getAllProjects();
    }

    void insert(Project project) {
        mProjectDao.insert(project);
    }

    void delete(Project project) {
        mProjectDao.delete(project.getId());
    }
}