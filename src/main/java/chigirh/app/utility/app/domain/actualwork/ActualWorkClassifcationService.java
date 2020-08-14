package chigirh.app.utility.app.domain.actualwork;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import chigirh.app.utility.app.mapper.actualwork.ActualWorkClassifcation1Mapper;
import chigirh.app.utility.app.mapper.actualwork.ActualWorkClassifcation2Mapper;
import chigirh.app.utility.app.mapper.actualwork.ActualWorkClassifcationJunctionMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActualWorkClassifcationService {

	final ActualWorkClassifcation1Mapper classifcation1Mapper;

	final ActualWorkClassifcation2Mapper classifcation2Mapper;

	final ActualWorkClassifcationJunctionMapper classifcationJunctionMapper;

	@Transactional
	public List<ActualWorkClassifcation1Entity> classifcation1Get() {
		return classifcation1Mapper.findAll();
	}

	@Transactional
	public ActualWorkClassifcation1Entity classifcation1Get(final String id) {
		if (id == null) {
			return null;
		}
		return classifcation1Mapper.findById(id).orElse(null);
	}

	@Transactional
	public void classifcation1Delete(final String id) {
		classifcationJunctionMapper.findByParentIdEquals(id).stream().map(ActualWorkClassifcationJunctionEntity::getKey)
				.forEach(classifcationJunctionMapper::deleteById);
		classifcation1Mapper.deleteById(id);
	}

	@Transactional
	public List<ActualWorkClassifcation2Entity> classifcation2Get() {

		return classifcation2Mapper.findAll();
	}

	@Transactional
	public ActualWorkClassifcation2Entity classifcation2Get(final String id) {
		if (id == null) {
			return null;
		}
		return classifcation2Mapper.findById(id).orElse(null);
	}

	@Transactional
	public void classifcation2Delete(final String id) {
		classifcationJunctionMapper.findByChildIdEquals(id).stream().map(ActualWorkClassifcationJunctionEntity::getKey)
				.forEach(classifcationJunctionMapper::deleteById);
		classifcation2Mapper.deleteById(id);
	}

	@Transactional
	public List<ActualWorkClassifcation1Entity> findByclassifcation2(final String classifcation2Id) {
		return classifcationJunctionMapper.findByChildIdEquals(classifcation2Id)//
				.parallelStream().map(ActualWorkClassifcationJunctionEntity::getParentId)
				.map(classifcation1Mapper::findById)//
				.filter(Optional::isPresent).map(Optional::get)//
				.collect(Collectors.toList());
	}

	@Transactional
	public List<ActualWorkClassifcation2Entity> findByclassifcation1(final String classifcation1Id) {
		return classifcationJunctionMapper.findByParentIdEquals(classifcation1Id)//
				.parallelStream().map(ActualWorkClassifcationJunctionEntity::getChildId)
				.map(classifcation2Mapper::findById)//
				.filter(Optional::isPresent).map(Optional::get)//
				.collect(Collectors.toList());
	}

	@Transactional
	public ActualWorkClassifcation1Entity classifcation1Add(String classifcationName) {
		ActualWorkClassifcation1Entity entity = new ActualWorkClassifcation1Entity();
		entity.setId(UUID.randomUUID().toString());
		entity.setName(classifcationName);
		return classifcation1Mapper.saveAndFlush(entity);
	}

	@Transactional
	public ActualWorkClassifcation2Entity classifcation2Add(final String classifcation1Id, String classifcation2Name) {
		ActualWorkClassifcation2Entity entity = new ActualWorkClassifcation2Entity();
		entity.setId(UUID.randomUUID().toString());
		entity.setName(classifcation2Name);
		classifcation2Mapper.saveAndFlush(entity);
		ActualWorkClassifcationJunctionEntity junctionEntity = new ActualWorkClassifcationJunctionEntity();
		junctionEntity.setParentId(classifcation1Id);
		junctionEntity.setChildId(entity.getId());
		classifcationJunctionMapper.saveAndFlush(junctionEntity);
		return entity;
	}

}
