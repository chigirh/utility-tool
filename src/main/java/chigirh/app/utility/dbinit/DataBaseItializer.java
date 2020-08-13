package chigirh.app.utility.dbinit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.taskmgr.TaskStatusEntity;
import chigirh.app.utility.app.mapper.taskmgr.TaskStatusMapper;
import chigirh.app.utility.dbinit.CreateTableSQL.Col;
import chigirh.app.utility.dbinit.CreateTableSQL.Fk;

@Component
public class DataBaseItializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseItializer.class);

	@Autowired
	SqlIssuer issuer;

	@Autowired
	TaskStatusMapper taskStatusMapper;

	public void init() {

		LOGGER.info("Database Initialize start ...");

		master();
		transaction();

	}

	private void master() {

		LOGGER.info("Master table Initialize start ...");

		CreateTableSQL actualWorkClassification1 = CreateTableSQL.CREATE_TABLE()
				.tableName("m_actual_work_classification1")//
				.column(Col.builder().index(1).name("classification1_id").type(String.class).isPk(true).build())//
				.column(Col.builder().index(2).name("classification1_name").type(String.class).isNotNull(true).build())//
				.column(Col.builder().index(3).name("remark").type(String.class).build())//
				.build();
		LOGGER.info("table name {}", actualWorkClassification1.getTableName());
		boolean isCreate = issuer.createTable(actualWorkClassification1);
		LOGGER.info("result:{}", isCreate ? "SUCCESS!!" : "EXEITS");

		CreateTableSQL actualWorkClassification2 = CreateTableSQL.CREATE_TABLE()
				.tableName("m_actual_work_classification2")//
				.column(Col.builder().index(1).name("classification2_id").type(String.class).isPk(true).build())//
				.column(Col.builder().index(2).name("classification2_name").type(String.class).isNotNull(true).build())//
				.column(Col.builder().index(3).name("remark").type(String.class).build())//
				.build();

		LOGGER.info("table name {}", actualWorkClassification2.getTableName());
		isCreate = issuer.createTable(actualWorkClassification2);
		LOGGER.info("result ", isCreate ? "SUCCESS!!" : "EXEITS");

		CreateTableSQL actualWorkClassificationJunction = CreateTableSQL.CREATE_TABLE()
				.tableName("m_actual_work_classification_junction")//
				.column(Col.builder().index(1).name("parent").type(String.class).isPk(true).build())//
				.column(Col.builder().index(2).name("child").type(String.class).isPk(true).build())//
				.fk(Fk.builder().col("parent").refTab("m_actual_work_classification1").refCol("classification1_id")
						.build())//
				.fk(Fk.builder().col("child").refTab("m_actual_work_classification2").refCol("classification2_id")
						.build())//
				.build();

		LOGGER.info("table name {}", actualWorkClassificationJunction.getTableName());
		isCreate = issuer.createTable(actualWorkClassificationJunction);
		LOGGER.info("result : {}", isCreate ? "SUCCESS!!" : "EXEITS");

		CreateTableSQL taskStatus = CreateTableSQL.CREATE_TABLE()
				.tableName("m_task_status")//
				.column(Col.builder().index(1).name("status_id").type(String.class).isPk(true).build())//
				.column(Col.builder().index(2).name("status").type(String.class).isNotNull(true).build())//
				.column(Col.builder().index(3).name("remark").type(String.class).build())//
				.build();

		LOGGER.info("table name {}", taskStatus.getTableName());
		isCreate = issuer.createTable(taskStatus);
		LOGGER.info("result : {}", isCreate ? "SUCCESS!!" : "EXEITS");

		LOGGER.info("Master table Initialize end ...");
	}

	private void transaction() {
		LOGGER.info("Transaction table Initialize start ...");

		CreateTableSQL actualWorkGroup = CreateTableSQL.CREATE_TABLE().tableName("t_actual_work_group")//
				.column(Col.builder().index(1).name("actual_work_group_id").type(String.class).isPk(true).build())//
				.column(Col.builder().index(2).name("actual_work_group_name").type(String.class).isNotNull(true)
						.build())//
				.column(Col.builder().index(3).name("remark").type(String.class).build())//
				.build();
		LOGGER.info("table name {}", actualWorkGroup.getTableName());
		boolean isCreate = issuer.createTable(actualWorkGroup);
		LOGGER.info("result:{}", isCreate ? "SUCCESS!!" : "EXEITS");

		CreateTableSQL actualWork = CreateTableSQL.CREATE_TABLE().tableName("t_actual_work")//
				.column(Col.builder().index(1).name("actual_work_id").type(String.class).isPk(true).build())//
				.column(Col.builder().index(2).name("actual_work_group_id").type(String.class).isNotNull(true).build())//
				.column(Col.builder().index(3).name("actual_work_date").type(Integer.class).isNotNull(true).build())//
				.column(Col.builder().index(4).name("remark").type(String.class).build())//
				.fk(Fk.builder().col("actual_work_group_id").refTab("t_actual_work_group")
						.refCol("actual_work_group_id")
						.build())//
				.build();
		LOGGER.info("table name {}", actualWork.getTableName());
		isCreate = issuer.createTable(actualWork);
		LOGGER.info("result:{}", isCreate ? "SUCCESS!!" : "EXEITS");

		CreateTableSQL actualWorkTask = CreateTableSQL.CREATE_TABLE().tableName("t_actual_work_task")//
				.column(Col.builder().index(1).name("actual_work_id").type(String.class).isPk(true).build())//
				.column(Col.builder().index(2).name("serial").type(Integer.class).isPk(true).build())//
				.column(Col.builder().index(3).name("classification1_id").type(String.class).build())//
				.column(Col.builder().index(4).name("classification2_id").type(String.class).build())//
				.column(Col.builder().index(5).name("task_name").type(String.class).build())//
				.column(Col.builder().index(6).name("task_time").type(Double.class).build())//
				.column(Col.builder().index(7).name("remark").type(String.class).build())//
				.fk(Fk.builder().col("actual_work_id").refTab("t_actual_work").refCol("actual_work_id")
						.build())//
				.fk(Fk.builder().col("classification1_id").refTab("m_actual_work_classification1")
						.refCol("classification1_id").setNull(true)
						.build())//
				.fk(Fk.builder().col("classification2_id").refTab("m_actual_work_classification2")
						.refCol("classification2_id").setNull(true)
						.build())//
				.build();
		LOGGER.info("table name {}", actualWorkTask.getTableName());
		isCreate = issuer.createTable(actualWorkTask);
		LOGGER.info("result:{}", isCreate ? "SUCCESS!!" : "EXEITS");

		CreateTableSQL taskGroup = CreateTableSQL.CREATE_TABLE().tableName("t_task_group")//
				.column(Col.builder().index(1).name("task_group_id").type(String.class).isPk(true).build())//
				.column(Col.builder().index(2).name("task_group_name").type(String.class).build())//
				.column(Col.builder().index(7).name("remark").type(String.class).build())//
				.build();
		LOGGER.info("table name {}", taskGroup.getTableName());
		isCreate = issuer.createTable(taskGroup);
		LOGGER.info("result:{}", isCreate ? "SUCCESS!!" : "EXEITS");

		CreateTableSQL task = CreateTableSQL.CREATE_TABLE().tableName("t_task")//
				.column(Col.builder().index(1).name("task_id").type(String.class).isPk(true).build())//
				.column(Col.builder().index(2).name("task_group_id").type(String.class).isPk(true).build())//
				.column(Col.builder().index(3).name("task_name").type(String.class).build())//
				.column(Col.builder().index(4).name("start_date").type(Integer.class).isNotNull(true).build())//
				.column(Col.builder().index(5).name("update_date").type(Integer.class).isNotNull(true).build())//
				.column(Col.builder().index(6).name("limit_date").type(Integer.class).build())//
				.column(Col.builder().index(7).name("status_id").type(String.class).isNotNull(true).build())//
				.column(Col.builder().index(8).name("remark").type(String.class).build())//
				.fk(Fk.builder().col("task_group_id").refTab("t_task_group").refCol("task_group_id").setNull(true)
						.build())//
				.fk(Fk.builder().col("status_id").refTab("m_task_status").refCol("status_id").setNull(true)
						.build())//
				.build();
		LOGGER.info("table name {}", task.getTableName());
		isCreate = issuer.createTable(task);
		LOGGER.info("result:{}", isCreate ? "SUCCESS!!" : "EXEITS");


			TaskStatusEntity taskStatusEntity = new TaskStatusEntity();
			taskStatusEntity.setStatusId("0");
			taskStatusEntity.setStatus("未着手");
			taskStatusMapper.saveAndFlush(taskStatusEntity);

			taskStatusEntity.setStatusId("1");
			taskStatusEntity.setStatus("着手中");
			taskStatusMapper.saveAndFlush(taskStatusEntity);

			taskStatusEntity.setStatusId("2");
			taskStatusEntity.setStatus("Rv中");
			taskStatusMapper.saveAndFlush(taskStatusEntity);

			taskStatusEntity.setStatusId("3");
			taskStatusEntity.setStatus("完了");
			taskStatusMapper.saveAndFlush(taskStatusEntity);

			taskStatusEntity.setStatusId("4");
			taskStatusEntity.setStatus("保留");
			taskStatusMapper.saveAndFlush(taskStatusEntity);

			taskStatusEntity.setStatusId("5");
			taskStatusEntity.setStatus("欠番");
			taskStatusMapper.saveAndFlush(taskStatusEntity);

		LOGGER.info("Transaction table Initialize end ...");

	}
}
