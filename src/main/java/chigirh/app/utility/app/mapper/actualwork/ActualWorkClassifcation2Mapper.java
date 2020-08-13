package chigirh.app.utility.app.mapper.actualwork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcation2Entity;

@Repository
public interface ActualWorkClassifcation2Mapper extends JpaRepository<ActualWorkClassifcation2Entity, String>{
}
