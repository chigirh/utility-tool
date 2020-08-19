package chigirh.app.utility.app.domain.shortcut;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_sc")
@Data
public class ShortcutEntity {

	@Id
	@Column(name = "sc_id")
	private String scId;

	@Column(name = "sc_group_id")
	private String scGroupId;

	@Column(name = "sc_title")
	private String scTitle;

	@Column(name = "sc_path")
	private String scPath;

	@Column(name = "remark")
	private String remark;

}
