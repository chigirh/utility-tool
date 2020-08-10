package chigirh.app.utility.javafx.window;

import chigirh.app.utility.javafx.presenter.PresenterBase;
import javafx.scene.Parent;
import lombok.RequiredArgsConstructor;

/**
 * rootとPresenterを保持する..
 * @author Hiroki.Chigira
 *
 */
@lombok.Getter
@RequiredArgsConstructor
public class ContentViewAndPresenter<E extends PresenterBase> {

	final Parent view;

	final E presenter;

}
