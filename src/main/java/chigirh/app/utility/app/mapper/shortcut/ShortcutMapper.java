package chigirh.app.utility.app.mapper.shortcut;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chigirh.app.utility.app.domain.shortcut.ShortcutEntity;

@Repository
public interface ShortcutMapper extends JpaRepository<ShortcutEntity, String>{

	public List<ShortcutEntity> findByScGroupIdEquals(String scGroupId);

}
