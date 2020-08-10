package chigirh.app.utility.javafx.window;

import chigirh.app.utility.javafx.presenter.PresenterBase;
import javafx.scene.Parent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * rootとPresenterを保持する..
 * @author Hiroki.Chigira
 *
 */
@lombok.Getter(value=AccessLevel.PACKAGE)
@AllArgsConstructor
public class ViewAndPresenter {

	final Parent view;

	final PresenterBase presenter;

}
