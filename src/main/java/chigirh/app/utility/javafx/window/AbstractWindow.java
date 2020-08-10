package chigirh.app.utility.javafx.window;

import chigirh.app.utility.javafx.presenter.PresenterBase;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *ウィンドウ基底クラス.
 * @author Hiroki.Chigira
 *
 */
public abstract class AbstractWindow {

	final ViewAndPresenter viewAndPresenter;

	final Stage stage;

	protected double x;

	protected double y;

	protected double height;

	protected double width;

	public AbstractWindow(ViewAndPresenter viewAndPresenter,Object windowParam,StageStyle style) {
		this.viewAndPresenter = viewAndPresenter;
		presenter().setWindow(this);
		this.stage = new Stage(style);
		stage.setScene(new Scene(viewAndPresenter.getView()));

		//ハンドラ登録
		stage.xProperty().addListener(this::handlePositionChange);
		stage.yProperty().addListener(this::handlePositionChange);

		stage.widthProperty().addListener(this::handleSizeChange);
		stage.heightProperty().addListener(this::handleSizeChange);

		stage.focusedProperty().addListener(this::handleFocusChange);

		presenter().setPatameter(windowParam);
		presenter().onStart();
	}

	protected final Parent view() {
		return viewAndPresenter.getView();
	}


	protected final PresenterBase presenter() {
		return viewAndPresenter.getPresenter();
	}

	private final void handlePositionChange(ObservableValue<? extends Number> listener,Number oldValue,Number newValue) {
		this.x = stage.getX();
		this.y = stage.getY();
	}

	private final void handleSizeChange(ObservableValue<? extends Number> listener,Number oldValue,Number newValue) {
		this.height = stage.getHeight();
		this.width = stage.getWidth();
	}

	private final void handleFocusChange(ObservableValue<? extends Boolean> listener,Boolean oldValue,Boolean newValue) {
		if(newValue) {
			presenter().onFocusIn();
			return;
		}

		presenter().onFocusOut();
	}

	public final AbstractWindow applyCss(String css) {
		view().getStylesheets().add(getClass().getResource(css).toExternalForm());
		return this;
	}

	public final void show() {
		presenter().onShowing();
		stage.show();
	}

	public final void close() {
		presenter().onClose();
		stage.close();
	}

	public final AbstractWindow resizable(boolean is) {
		stage.setResizable(is);
		return this;
	}


}
