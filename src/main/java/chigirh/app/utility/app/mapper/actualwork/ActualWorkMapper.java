package chigirh.app.utility.app.mapper.actualwork;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chigirh.app.utility.app.domain.actualwork.ActualWorkEntity;

@Repository
public interface ActualWorkMapper extends JpaRepository<ActualWorkEntity, String>{

	public List<ActualWorkEntity> findByAwGroupIdEquals(String awGroupId);

}
