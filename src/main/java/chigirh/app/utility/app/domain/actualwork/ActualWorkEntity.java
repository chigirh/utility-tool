package chigirh.app.utility.app.domain.actualwork;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_actual_work")
@Data
public class ActualWorkEntity {

	@Id
	@Column(name = "actual_work_id")
	private String awId;

	@Column(name = "actual_work_group_id")
	private String awGroupId;

	@Column(name = "actual_work_date")
	private Long awDate;

	@Column(name = "remark")
	private String remark;

}
