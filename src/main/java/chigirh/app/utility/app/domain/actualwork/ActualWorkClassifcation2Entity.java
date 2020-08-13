package chigirh.app.utility.app.domain.actualwork;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "m_actual_work_classification2")
@Data
public class ActualWorkClassifcation2Entity {

	@Id
	@Column(name = "classification2_id")
	private String id;

	@Column(name = "classification2_name")
	private String name;

	@Column(name = "remark")
	private String remark;

}
