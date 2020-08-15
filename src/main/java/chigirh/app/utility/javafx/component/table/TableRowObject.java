package chigirh.app.utility.javafx.component.table;

import java.util.function.Supplier;

import chigirh.app.utility.javafx.component.table.TableRow.RowType;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class TableRowObject<E extends TableRowObject<E,V>,V extends SimpleTableRow<?>> {

	protected V vm;

	private HBox hBox;

	private Supplier<HBox> rowFactory;

	private final RowType rowType;

	private boolean isDisplay = false;

	private boolean isVisible = false;

	private E prev = null;

	private E next = null;

	public boolean isFirst() {
		return prev == null;
	}

	public boolean isLast() {
		return next == null;
	}

	public E prev() {
		return prev;
	}

	public E next() {
		return next;
	}

	public final HBox createRow() {
		if(hBox == null) {
			hBox = rowFactory.get();
		}
		return hBox;
	}




}
