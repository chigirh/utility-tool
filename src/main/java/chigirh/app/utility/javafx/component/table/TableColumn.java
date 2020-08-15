package chigirh.app.utility.javafx.component.table;

import java.util.Comparator;
import java.util.function.Function;

import javafx.beans.property.ReadOnlyProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class TableColumn<R extends SimpleTableRow<?>, P extends ReadOnlyProperty<?>, T> {

	private String columnName;

	private int order;

	private Comparator<TableRowObject<?, R>> sortCondition;

	private double width;

	private boolean isEditable;

	private Function<R, P> propertyFactory;

	public TableCell<?, ?> cellCreate(R row) {
		TableCell<?, ?> cell = getCell();
		P prop = propertyFactory.apply(row);
		binding(cell, prop, row);
		return cell;
	}

	protected abstract TableCell<?, ?> getCell();

	public abstract void binding(TableCell<?, ?> cell, P prop, R row);

}
