package chigirh.app.utility.dataacces.dataaccess;

import java.util.List;

import chigirh.app.utility.dataacces.common.manipulation.InsertQuery;
import chigirh.app.utility.dataacces.common.manipulation.UpdateQuery;

public interface DataAccess {

	public List<ResponseModel> select();

	public void insert(InsertQuery insertQuery);

	public void update(UpdateQuery updateQuery);

}
