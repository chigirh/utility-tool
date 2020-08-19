package chigirh.app.utility.app.domain.shortcut;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import chigirh.app.utility.app.mapper.shortcut.ShortcutGroupMapper;
import chigirh.app.utility.app.mapper.shortcut.ShortcutMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShortcutService {

	final ShortcutGroupMapper shortcutGroupMapper;

	final ShortcutMapper shortcutMapper;

	public List<ShortcutGroupEntity> scGroupGet() {
		return shortcutGroupMapper.findAll();
	}

	public ShortcutGroupEntity scGroupAdd(String scGroupName) {
		ShortcutGroupEntity entity = new ShortcutGroupEntity();
		entity.setScGroupId(UUID.randomUUID().toString());
		entity.setScGroupName(scGroupName);
		return shortcutGroupMapper.saveAndFlush(entity);
	}

	public void scGroupDelete(final String scGroupId) {
		shortcutGroupMapper.deleteById(scGroupId);
		scGet(scGroupId).stream().map(ShortcutEntity::getScId).forEach(this::scDelete);
	}

	public List<ShortcutEntity> scGet(final String scGroupId) {
		return shortcutMapper.findByScGroupIdEquals(scGroupId);

	}

	public ShortcutEntity scAdd(final String scGroupId, String scTitle, String scPath) {
		ShortcutEntity entity = new ShortcutEntity();
		entity.setScId(UUID.randomUUID().toString());
		entity.setScGroupId(scGroupId);
		entity.setScTitle(scTitle);
		entity.setScPath(scPath);
		return shortcutMapper.saveAndFlush(entity);

	}

	public ShortcutEntity scUpdate(final String scId, final String scGroupId, String scTitle, String scPath) {
		ShortcutEntity entity = new ShortcutEntity();
		entity.setScId(scId);
		entity.setScGroupId(scGroupId);
		entity.setScTitle(scTitle);
		entity.setScPath(scPath);
		return shortcutMapper.saveAndFlush(entity);

	}

	public void scDelete(final String scId) {
		shortcutMapper.deleteById(scId);

	}
}
