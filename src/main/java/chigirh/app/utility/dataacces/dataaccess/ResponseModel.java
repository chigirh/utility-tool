package chigirh.app.utility.dataacces.dataaccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import chigirh.app.utility.dataacces.common.common.DataModel;
import chigirh.app.utility.dataacces.common.definition.ColumnDefinition;
import lombok.ToString;

/**
 * 取得したデータの戻り値
 */
@ToString
public class ResponseModel implements DataModel{

	private final Map<String,String> model;

	public ResponseModel(String[] values,List<ColumnDefinition> columns) {
		this.model = columns.stream().collect(Collectors.toMap(ColumnDefinition::getFqcn,e -> values[e.getIndex()]));
	}

	public ResponseModel(ResponseModel origin,ResponseModel destiination) {
		this.model = new HashMap<>(origin.model);
		model.putAll(destiination.model);
	}

	public void update(Map<String, String> updateModel) {
		model.putAll(updateModel);
	}

	@Override
	public String getValue(String fqcn) {
		return model.get(fqcn);
	}

}
