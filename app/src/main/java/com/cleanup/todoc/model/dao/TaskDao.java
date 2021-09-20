package com.cleanup.todoc.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> fetchAllTasks();

    @Insert
    long insert(Task task);

    @Query("DELETE FROM Task WHERE task_id = :taskId")
    int delete(long taskId);
}
