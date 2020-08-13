package chigirh.app.utility.app.mapper.actualwork;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcationJunctionEntity;

@Repository
public interface ActualWorkClassifcationJunctionMapper extends JpaRepository<ActualWorkClassifcationJunctionEntity, ActualWorkClassifcationJunctionEntity.PrimaryKey>{

	public List<ActualWorkClassifcationJunctionEntity> findByParentIdEquals(String parentId);

	public List<ActualWorkClassifcationJunctionEntity> findByChildIdEquals(String childId);

}


