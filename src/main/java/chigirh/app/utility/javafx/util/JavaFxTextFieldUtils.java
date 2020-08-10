package chigirh.app.utility.javafx.util;

import java.util.regex.Pattern;

import javafx.css.PseudoClass;
import javafx.scene.control.TextField;

public class JavaFxTextFieldUtils {

	private static final PseudoClass ERROR = PseudoClass.getPseudoClass("error");

	public static boolean inputCheck(TextField textField, String regex) {

		Pattern p = Pattern.compile(regex);

		if (p.matcher(textField.getText()).find()) {
			textField.pseudoClassStateChanged(ERROR, false);
			return true;
		}

		textField.pseudoClassStateChanged(ERROR, true);
		return false;
	}

}
