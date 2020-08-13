package chigirh.app.utility.app.mapper.actualwork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcation1Entity;

@Repository
public interface ActualWorkClassifcation1Mapper extends JpaRepository<ActualWorkClassifcation1Entity, String>{
}
