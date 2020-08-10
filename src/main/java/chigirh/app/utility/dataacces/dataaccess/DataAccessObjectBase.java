package chigirh.app.utility.dataacces.dataaccess;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chigirh.app.utility.dataacces.common.definition.OperationTaskColumn;
import chigirh.app.utility.dataacces.common.definition.TableDefinition;
import chigirh.app.utility.dataacces.common.exception.DataAccessError;
import chigirh.app.utility.dataacces.common.exception.DataAccessException;
import chigirh.app.utility.dataacces.common.helper.DataRowHelper;
import chigirh.app.utility.dataacces.common.manipulation.InsertQuery;
import chigirh.app.utility.dataacces.common.manipulation.UpdateQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DataAccessObjectBase implements DataAccess {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessObjectBase.class);

	final DataRowHelper dataRowHelper;

	public List<ResponseModel> select() {

		LOGGER.info("data access for:{}", getDataPath());

		try (Stream<String> dataLine = Files.lines(Paths.get(getDataPath()));) {
			return dataLine.parallel().map(this::responseModel).collect(Collectors.toList());
		} catch (IOException e) {
			return new ArrayList<>();
		}
	}

	@Override
	public void insert(InsertQuery insertQuery) {

		LOGGER.info("data access for:{}", getDataPath());

		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(getDataPath(), true)));) {
			dataRowHelper.constraintCheck(insertQuery);
			pw.println(dataRowHelper.toRow(insertQuery.getTable(), insertQuery));
		} catch (IOException e) {
			e.printStackTrace();
			throw new DataAccessException(DataAccessError.CONNECTION_ERROR);
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	public void update(UpdateQuery updateQuery) {

		try (Stream<String> dataLine = Files.lines(Paths.get(getDataPath()));) {
			List<ResponseModel> updateList = dataLine.map(e -> updateMap(e, updateQuery)).collect(Collectors.toList());

			try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(getDataPath(), false)));) {
				updateList.forEach(e -> pw.println(dataRowHelper.toRow(updateQuery.getTable(), e)));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new DataAccessException(DataAccessError.CONNECTION_ERROR);
		} catch (DataAccessException e) {
			throw e;
		}

	}

	private ResponseModel responseModel(String line) {
		return new ResponseModel(line.split(DataRowHelper.COMMA), getTable().getColumns());
	}

	private ResponseModel updateMap(String line, UpdateQuery query) {
		ResponseModel responseModel = new ResponseModel(line.split(DataRowHelper.COMMA), getTable().getColumns());
		System.out.println(OperationTaskColumn.OPERATION_ID.getValue(responseModel));
		System.out.println(OperationTaskColumn.SERIAL_NUMBER.getValue(responseModel));
		if (!query.getWhere().test(responseModel)) {
			return responseModel;
		}

		ResponseModel updateModel = new ResponseModel(line.split(DataRowHelper.COMMA), getTable().getColumns());
		updateModel.update(query.getParams());


		try {
			dataRowHelper.constraintCheck(query);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return responseModel;
		}

		return updateModel;
	}

	protected abstract String getDataPath();

	protected abstract TableDefinition getTable();

}
