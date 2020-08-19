package chigirh.app.utility.app.mapper.shortcut;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chigirh.app.utility.app.domain.shortcut.ShortcutGroupEntity;

@Repository
public interface ShortcutGroupMapper extends JpaRepository<ShortcutGroupEntity, String>{

}
