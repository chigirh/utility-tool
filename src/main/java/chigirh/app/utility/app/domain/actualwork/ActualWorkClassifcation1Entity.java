package chigirh.app.utility.app.domain.actualwork;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "m_actual_work_classification1")
@Data
public class ActualWorkClassifcation1Entity {

	@Id
	@Column(name = "classification1_id")
	private String id;

	@Column(name = "classification1_name")
	private String name;

	@Column(name = "remark")
	private String remark;

}
