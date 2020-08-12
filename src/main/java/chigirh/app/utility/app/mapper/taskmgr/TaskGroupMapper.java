package chigirh.app.utility.app.mapper.taskmgr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chigirh.app.utility.app.domain.taskmgr.TaskGroupEntity;

@Repository
public interface TaskGroupMapper extends JpaRepository<TaskGroupEntity, String>{

}
