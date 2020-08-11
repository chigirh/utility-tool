package chigirh.app.utility.app.domain.taskmgr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "m_task_status")
@Data
public class TaskStatusEntity {

	@Id
	@Column(name = "status_id")
	private String statusId;

	@Column(name = "status")
	private String status;

	@Column(name = "remark")
	private String remark;

}
