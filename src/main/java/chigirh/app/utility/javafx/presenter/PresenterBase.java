package chigirh.app.utility.javafx.presenter;

import java.util.ArrayList;
import java.util.List;

import chigirh.app.utility.boot.ApplicationContextSupport;
import chigirh.app.utility.javafx.helper.FxmlLoadHelper;
import chigirh.app.utility.javafx.window.AbstractWindow;
import chigirh.app.utility.javafx.window.ContentViewAndPresenter;
import javafx.fxml.Initializable;
import lombok.Setter;

/**
 * 画面プレゼンターの基底クラス.
 * @author Hiroki.Chigira
 *
 */
public abstract class PresenterBase implements Initializable{

	@Setter
	protected AbstractWindow window;

	private List<PresenterBase> children = new ArrayList<>();

	public void setPatameter(Object windowParam) {
	}

	/**
	 * 初期表示.
	 */
	public  void onStart() {
		children.forEach(PresenterBase::onStart);
	}

	/**
	 * 画面表示.
	 */
	public  void onShowing() {
		children.forEach(PresenterBase::onShowing);
	}

	/**
	 * 画面クローズ.
	 */
	public  void onClose() {
		children.forEach(PresenterBase::onClose);
	}

	/**
	 * フォーカスイン
	 */
	public  void onFocusIn() {
		children.forEach(PresenterBase::onFocusIn);
	}

	/**
	 * フォーカスアウト.
	 */
	public  void onFocusOut() {
		children.forEach(PresenterBase::onFocusOut);
	}

	protected final <E extends PresenterBase> ContentViewAndPresenter<E> loadContent(final String fxml) {
		 final FxmlLoadHelper fxmlLoadHelper = ApplicationContextSupport.getBean(FxmlLoadHelper.class);
		 ContentViewAndPresenter<E> vp = fxmlLoadHelper.<E>loadContent(fxml);
		 children.add(vp.getPresenter());
		 return vp;

	}

}
