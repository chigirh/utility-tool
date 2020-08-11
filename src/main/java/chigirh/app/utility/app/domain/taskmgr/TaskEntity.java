package chigirh.app.utility.app.domain.taskmgr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_task")
@Data
public class TaskEntity {

	@Id
	@Column(name = "task_id")
	private String taskId;

	@Column(name = "task_name")
	private String taskName;

	@Column(name = "start_date")
	private Long startDate;

	@Column(name = "update_date")
	private Long updateDate;

	@Column(name = "limit_date")
	private Long limitDate;

	@Column(name = "status_id")
	private String statusId;

	@Column(name = "remark")
	private String remark;

}
