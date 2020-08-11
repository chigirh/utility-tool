package chigirh.app.utility.javafx.window;

import javafx.stage.StageStyle;

/**
 *通常画面用ウィンドウクラス.
 * @author Hiroki.Chigira
 *
 */
public class NormarWIndow extends AbstractWindow{

	public NormarWIndow(final ViewAndPresenter viewAndPresenter,Object windowParam) {
		super(viewAndPresenter,windowParam,StageStyle.DECORATED);
	}



}
