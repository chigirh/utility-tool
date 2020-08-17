package chigirh.app.utility.app.domain.actualwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.OptionalInt;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import chigirh.app.utility.app.mapper.actualwork.ActualWorkGroupMapper;
import chigirh.app.utility.app.mapper.actualwork.ActualWorkMapper;
import chigirh.app.utility.app.mapper.actualwork.ActualWorkTaskMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActualWorkService {

	SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

	final ActualWorkGroupMapper actualWorkGroupMapper;

	final ActualWorkMapper actualWorkMapper;

	final ActualWorkTaskMapper actualWorkTaskMapper;

	@Transactional
	public List<ActualWorkGroupEntity> awGroupGet() {
		return actualWorkGroupMapper.findAll();
	}

	@Transactional
	public ActualWorkGroupEntity awGroupAdd(String awGroupName, String remark) {
		ActualWorkGroupEntity entity = new ActualWorkGroupEntity();
		entity.setAwGroupId(UUID.randomUUID().toString());
		entity.setAwGroupName(awGroupName);
		entity.setRemark(remark);
		return actualWorkGroupMapper.saveAndFlush(entity);
	}

	@Transactional
	public List<ActualWorkEntity> awGet(final String awGroupId) {
		return actualWorkMapper.findByAwGroupIdEquals(awGroupId);
	}

	@Transactional
	public double awTaskTimeSumGet(final String awId) {
		return awTaskGet(awId).stream().mapToDouble(ActualWorkTaskEntity::getTaskTime).sum();
	}

	@Transactional
	public List<ActualWorkTaskEntity> awTaskGet(final String awId) {
		return actualWorkTaskMapper.findByAwIdEquals(awId);
	}

	@Transactional
	public ActualWorkEntity awAdd(String awGroupId, String awDate, String remark) throws ParseException {
		ActualWorkEntity entity = new ActualWorkEntity();
		entity.setAwId(UUID.randomUUID().toString());
		entity.setAwGroupId(awGroupId);
		entity.setAwDate(SDF.parse(awDate).getTime());
		entity.setRemark(remark);
		return actualWorkMapper.saveAndFlush(entity);
	}

	@Transactional
	public ActualWorkTaskEntity awTaskAdd(final String awId) {
		ActualWorkTaskEntity entity = new ActualWorkTaskEntity();
		entity.setAwId(awId);
		entity.setSerial(awTaskSerialGet(awId));
		entity.setTaskTime(0.0);
		return actualWorkTaskMapper.saveAndFlush(entity);
	}

	@Transactional
	public int awTaskSerialGet(final String awId) {
		OptionalInt serialOpt = awTaskGet(awId).stream().mapToInt(ActualWorkTaskEntity::getSerial).max();
		return serialOpt.orElse(0) + 1;
	}

	@Transactional
	public void awUpdate(ActualWorkEntity entity, String awDate) throws ParseException {
		entity.setAwDate(SDF.parse(awDate).getTime());
		actualWorkMapper.saveAndFlush(entity);
	}

	@Transactional
	public void awTaskUpdate(ActualWorkTaskEntity entity, final String classifcation1Id, final String classifcation2Id,
			String taskName, String taskTime) {
		entity.setClassification1(classifcation1Id);
		entity.setClassification2(classifcation2Id);
		entity.setTaskName(taskName);
		entity.setTaskTime(Double.parseDouble(taskTime));
		actualWorkTaskMapper.saveAndFlush(entity);
	}

	@Transactional
	public void awGroupDelete(final String awGroupId) {
		actualWorkGroupMapper.deleteById(awGroupId);
		awGet(awGroupId).stream().map(ActualWorkEntity::getAwId).forEach(this::awDelete);
	}

	@Transactional
	public void awDelete(final String awId) {
		actualWorkMapper.deleteById(awId);
		awTaskGet(awId).stream().forEach(e -> awTaskDelete(e.getAwId(), e.getSerial()));
	}

	@Transactional
	public void awTaskDelete(final String awId, final int serial) {
		ActualWorkTaskEntity.PrimaryKey pk = new ActualWorkTaskEntity.PrimaryKey();
		pk.setAwId(awId);
		pk.setSerial(serial);
		actualWorkTaskMapper.deleteById(pk);
		;
	}

}
