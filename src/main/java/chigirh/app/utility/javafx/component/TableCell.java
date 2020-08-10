package chigirh.app.utility.javafx.component;

import javafx.beans.property.ReadOnlyProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

public abstract class TableCell<E extends Control, P extends ReadOnlyProperty<?>> extends AnchorPane {

	private static final String STYLE_CLASS = "utl-table-cell";

	@Getter
	protected E cell;

	protected TableCell() {
		getStyleClass().add(STYLE_CLASS);
	}

	protected void setCell(E cell) {
		getChildren().add(cell);
		this.cell = cell;
	}

	protected void setError(boolean isError) {
		pseudoClassStateChanged(PseudoClassConstans.ERROR, isError);
	}

	public abstract void change(Runnable task);

	public abstract P getProperty();

	public void setWidth(double width) {
		cell.setPrefWidth(width);
	}

	public final <T extends Event> void addEvenet(
			final EventType<T> eventType,
			final EventHandler<? super T> eventHandler) {
		cell.addEventFilter(eventType, eventHandler);
	}

}
