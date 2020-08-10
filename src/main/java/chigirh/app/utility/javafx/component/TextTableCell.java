package chigirh.app.utility.javafx.component;

import java.util.function.Predicate;

import chigirh.app.utility.javafx.component.actualwork.UtlTextField;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import lombok.Setter;

public class TextTableCell extends TableCell<TextField, StringProperty> {

	@Setter
	private Predicate<TextField> validator = null;

	public TextTableCell(boolean isEditable) {
		this("", isEditable);
	}

	public TextTableCell(String text) {
		this(text, false);
	}

	public TextTableCell(String text, boolean isEditable) {
		super();
		setCell(new UtlTextField());
		getCell().setText(text);
		getCell().setEditable(isEditable);

		getCell().focusedProperty().addListener((ob, ov, nv) -> {
			if (nv)
				if(!getCell().isEditable()){
					requestFocus();
				}
		});
	}

	public void change(Runnable task) {
		getCell().focusedProperty().addListener((ob, ov, nv) -> {
			if (!nv)
				if((validator == null || validator.test(getCell()))) {
					setError(false);
				task.run();
				}else {
					setError(true);
				}
		});
		getCell().setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				requestFocus();
		});
	}

	@Override
	public StringProperty getProperty() {
		return getCell().textProperty();
	}

}
