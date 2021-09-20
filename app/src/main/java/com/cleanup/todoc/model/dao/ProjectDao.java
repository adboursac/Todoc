package com.cleanup.todoc.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;
import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> fetchAllProjects();

    @Insert
    long insert(Project project);

    @Query("DELETE FROM Project WHERE project_id = :projectId")
    int delete(long projectId);

    @Query("DELETE FROM Project")
    void deleteAll();
}