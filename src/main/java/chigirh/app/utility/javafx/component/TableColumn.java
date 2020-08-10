package chigirh.app.utility.javafx.component;

import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.property.ReadOnlyProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class TableColumn<E,R extends SimpleTableRow<?>, P extends ReadOnlyProperty<?>,T> {

	private String columnName;

	private int order;

	private double width;

	private boolean isEditable;

	private Function<E, T> cellFactory;

	private Function<R, P> propertyFactory;

	private BiConsumer<R,T> valueSetter;

	public TableCell<?,?> cellCreate(E entity,R row) {
		TableCell<?,?> cell = getCell();
		P prop = propertyFactory.apply(row);
		valueSetter.accept(row,cellFactory.apply(entity));
		binding(cell, prop);
		return cell;
	}


	protected abstract TableCell<?,?> getCell();

	public abstract void binding(TableCell<?,?> cell, P prop);

}
