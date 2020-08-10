package chigirh.app.utility.app.mapper.actualwork;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chigirh.app.utility.app.domain.actualwork.ActualWorkTaskEntity;

@Repository
public interface ActualWorkTaskMapper extends JpaRepository<ActualWorkTaskEntity, ActualWorkTaskEntity.PrimaryKey>{

	public List<ActualWorkTaskEntity> findByAwIdEquals(String awId);

}
