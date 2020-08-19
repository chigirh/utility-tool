package chigirh.app.utility.app.domain.shortcut;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_sc_group")
@Data
public class ShortcutGroupEntity {

	@Id
	@Column(name = "sc_group_id")
	private String scGroupId;

	@Column(name = "sc_group_name")
	private String scGroupName;

	@Column(name = "remark")
	private String remark;

}
