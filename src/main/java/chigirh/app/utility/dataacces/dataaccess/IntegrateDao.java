package chigirh.app.utility.dataacces.dataaccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import chigirh.app.utility.dataacces.common.common.DataModel;
import chigirh.app.utility.dataacces.common.manipulation.InsertQuery;
import chigirh.app.utility.dataacces.common.manipulation.SelectQuery;
import chigirh.app.utility.dataacces.common.manipulation.SelectQuery.JoinQuery;
import chigirh.app.utility.dataacces.common.manipulation.UpdateQuery;

@Component
public class IntegrateDao {

	public List<ResponseModel> select(SelectQuery query) {

		List<ResponseModel> fromResponses = query.getFrom().getDao().select();

		query.getJoin().forEach(e -> tableJoin(e, fromResponses));

		Predicate<DataModel> where = query.getWhere();

		List<ResponseModel> whereResponses = new ArrayList<ResponseModel>(//
				where == null ? fromResponses//
						: fromResponses.parallelStream().filter(where::test).collect(Collectors.toList()));

		Comparator<DataModel> orderBy = query.getOrderBy();

		if (orderBy != null) {
			whereResponses.sort(orderBy);
		}

		return whereResponses;

	}

	public boolean insert(InsertQuery query) {
		try {
			query.getTable().getDao().insert(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void update(UpdateQuery query) {
		try {
			query.getTable().getDao().update(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void tableJoin(JoinQuery query, List<ResponseModel> fromResponses) {

		List<ResponseModel> joinResponses = query.getTable().getDao().select();

		Collection<ResponseModel> tmp = Collections.unmodifiableCollection(fromResponses);
		fromResponses.clear();

		fromResponses.addAll(//
				tmp.parallelStream().flatMap(
						left -> //
						joinResponses.stream().filter(right -> query.getJoin().test(left, right))
								.map(right -> merge(left, right)))
						.collect(Collectors.toList())//
		);

	}

	private ResponseModel merge(ResponseModel left, ResponseModel right) {
		return new ResponseModel(left, right);
	}

}
