package chigirh.app.utility.app.domain.actualwork;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_actual_work_group")
@Data
public class ActualWorkGroupEntity {

	@Id
	@Column(name = "actual_work_group_id")
	private String awGroupId;

	@Column(name = "actual_work_group_name")
	private String awGroupName;

	@Column(name = "remark")
	private String remark;

}
