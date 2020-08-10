package chigirh.app.utility.dataacces.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chigirh.app.utility.common.prop.DataProperties;
import chigirh.app.utility.dataacces.common.definition.TableDefinition;
import chigirh.app.utility.dataacces.common.helper.DataRowHelper;
import lombok.NonNull;

@Component("operationGroupDao")
public class OperationGroupDao extends DataAccessObjectBase {

	final DataProperties dataProperties;

	@Autowired
	public OperationGroupDao(@NonNull DataRowHelper dataRowHelper,@NonNull DataProperties dataProperties) {
		super(dataRowHelper);
		this.dataProperties = dataProperties;
	}

	@Override
	protected String getDataPath() {
		return dataProperties.getOperationGroup();
	}

	@Override
	protected TableDefinition getTable() {
		return TableDefinition.OPERATION_GROUP;
	}

}
