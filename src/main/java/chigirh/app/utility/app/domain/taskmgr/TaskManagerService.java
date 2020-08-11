package chigirh.app.utility.app.domain.taskmgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import chigirh.app.utility.app.mapper.taskmgr.TaskMapper;
import chigirh.app.utility.app.mapper.taskmgr.TaskStatusMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskManagerService {

	public static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");

	public static final SimpleDateFormat SDF_TIME = new SimpleDateFormat("yyyy-MM-dd:hh:mm");

	private static final Pattern SDF_DATE_PAT = //
			Pattern.compile("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$");

	private static final Pattern SDF_TIME_PAT = //
			Pattern.compile("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]):([0-1][0-9]|2[0-4]):([0-5][0-9]|60)$");

	final TaskStatusMapper taskStatusMapper;

	final TaskMapper taskMapper;

	public TaskEntity taskAdd(String taskName, String limitDate) {
		TaskEntity entity = new TaskEntity();
		entity.setTaskId(UUID.randomUUID().toString());
		entity.setTaskName(taskName);
		Date nowTime = new Date();
		entity.setStartDate(nowTime.getTime());
		entity.setUpdateDate(nowTime.getTime());
		entity.setStatusId("0");
		try {
			if (isDateFormatTime(limitDate)) {
				entity.setLimitDate(SDF_TIME.parse(limitDate).getTime());
			}
			if (isDateFormatDate(limitDate)) {
				entity.setLimitDate(SDF_DATE.parse(limitDate).getTime());
			}
		} catch (ParseException e) {
			new RuntimeException(e);
		}

		return taskMapper.saveAndFlush(entity);

	}

	public TaskEntity taskUpdate(final String taskId, String taskName, String limitDate, String statusId) {
		TaskEntity entity = new TaskEntity();
		entity.setTaskId(taskId);
		entity.setTaskName(taskName);
		Date nowTime = new Date();
		entity.setStartDate(nowTime.getTime());
		entity.setUpdateDate(nowTime.getTime());
		entity.setStatusId(statusId);
		try {
			if (isDateFormatTime(limitDate)) {
				entity.setLimitDate(SDF_TIME.parse(limitDate).getTime());
			}
			if (isDateFormatDate(limitDate)) {
				entity.setLimitDate(SDF_DATE.parse(limitDate).getTime());
			}
		} catch (ParseException e) {
			new RuntimeException(e);
		}

		return taskMapper.saveAndFlush(entity);

	}

	public void taskDelete(final String taskId) {
		taskMapper.deleteById(taskId);

	}

	public Optional<TaskStatusEntity> getStatus(final String statusId) {
		return taskStatusMapper.findById(statusId);
	}

	public boolean isDateFormatTime(String date) {
		return SDF_TIME_PAT.matcher(date).find();
	}

	public boolean isDateFormatDate(String date) {
		return SDF_DATE_PAT.matcher(date).find();
	}

}
