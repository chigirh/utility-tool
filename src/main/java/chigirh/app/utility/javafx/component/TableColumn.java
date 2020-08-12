package chigirh.app.utility.javafx.component;

import java.util.function.Function;

import javafx.beans.property.ReadOnlyProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class TableColumn<R extends SimpleTableRow<?>, P extends ReadOnlyProperty<?>,T> {

	private String columnName;

	private int order;

	private double width;

	private boolean isEditable;

	private Function<R, P> propertyFactory;

	public UtlTableCell<?,?> cellCreate(R row) {
		UtlTableCell<?,?> cell = getCell();
		P prop = propertyFactory.apply(row);
		binding(cell, prop,row);
		return cell;
	}


	protected abstract UtlTableCell<?,?> getCell();

	public abstract void binding(UtlTableCell<?,?> cell, P prop,R row);

}
