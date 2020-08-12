package chigirh.app.utility.javafx.component;

import chigirh.app.utility.javafx.util.JavaFxTextFieldUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import lombok.Setter;

public class UtlTextField extends TextField {

	private static final String STYLE_CLASS = "utl-text-field";

	@Setter
	private String validator = null;

	private BooleanProperty confirmNoticeProperty = new SimpleBooleanProperty(this, "confirmNoticeProperty", false);

	public UtlTextField() {
		this("");
	}

	public UtlTextField(String text) {
		super(text);
		getStyleClass().add(STYLE_CLASS);

		focusedProperty().addListener((ob, ov, nv) -> {
			if (ov)
				if (validator == null || JavaFxTextFieldUtils.inputCheck(this, validator)) {
					pseudoClassStateChanged(PseudoClassConstans.ERROR, false);
					confirmNoticeProperty.set(!confirmNoticeProperty.get());
				} else {
					pseudoClassStateChanged(PseudoClassConstans.ERROR, true);
				}
			if (nv) {
				pseudoClassStateChanged(PseudoClassConstans.ERROR, false);
			}
		});
		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				getParent().requestFocus();
		});
	}

	public BooleanProperty confirmNoticeProperty() {
		return confirmNoticeProperty;
	}

}
