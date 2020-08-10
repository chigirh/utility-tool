package chigirh.app.utility.app.mapper.actualwork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chigirh.app.utility.app.domain.actualwork.ActualWorkGroupEntity;

@Repository
public interface ActualWorkGroupMapper extends JpaRepository<ActualWorkGroupEntity, String>{

}
