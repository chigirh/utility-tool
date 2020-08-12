package chigirh.app.utility.app.mapper.taskmgr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chigirh.app.utility.app.domain.taskmgr.TaskEntity;

@Repository
public interface TaskMapper extends JpaRepository<TaskEntity, String>{

	public List<TaskEntity> findByTaskGroupIdEquals(String taskGroupId);

}
