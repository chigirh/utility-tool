package chigirh.app.utility.dbinit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chigirh.app.utility.dbinit.CreateTableSQL.Col;
import chigirh.app.utility.dbinit.CreateTableSQL.Fk;

@Component
public class DataBaseItializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseItializer.class);

	@Autowired
	SqlIssuer issuer;


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
				.tableName("m_actual_work_classificationJunction")//
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

		LOGGER.info("Transaction table Initialize end ...");

	}
}
