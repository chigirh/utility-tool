package chigirh.app.utility.app.domain.taskmgr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_task_group")
@Data
public class TaskGroupEntity {

	@Id
	@Column(name = "task_group_id")
	private String taskGroupId;

	@Column(name = "task_group_name")
	private String taskGroupName;

	@Column(name = "remark")
	private String remark;

}
