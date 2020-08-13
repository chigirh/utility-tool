package chigirh.app.utility.app.domain.actualwork;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_actual_work_classification_junction")
@IdClass(ActualWorkClassifcationJunctionEntity.PrimaryKey.class)
@Data
public class ActualWorkClassifcationJunctionEntity {

	@Id
	@Column(name = "parent")
	private String parentId;

	@Id
	@Column(name = "child")
	private String childId;

	public PrimaryKey getKey() {
		return new PrimaryKey(parentId,childId);
	}


	@Embeddable
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class PrimaryKey implements Serializable {

		@Column(name = "parent")
		private String parentId;

		@Column(name = "child")
		private String childId;

	}

}
