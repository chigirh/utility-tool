package chigirh.app.utility.app.domain.actualwork;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_actual_work_task")
@IdClass(ActualWorkTaskEntity.PrimaryKey.class)
@Data
public class ActualWorkTaskEntity {

	@Id
	@Column(name = "actual_work_id")
	private String awId;

	@Id
	@Column(name = "serial")
	private Integer serial;

	@Column(name = "classification1_id")
	private String classification1;

	@Column(name = "classification2_id")
	private String classification2;

	@Column(name = "task_name")
	private String taskName;

	@Column(name = "task_time")
	private Double taskTime;

	@Column(name = "remark")
	private String remark;

	@Embeddable
	@Data
	public static class PrimaryKey implements Serializable {

		@Column(name = "actual_work_id")
		private String awId;

		@Column(name = "serial")
		private Integer serial;

	}

}
