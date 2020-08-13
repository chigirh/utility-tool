package chigirh.app.utility.javafx.presenter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import chigirh.app.utility.javafx.component.SimpleTableRow;
import chigirh.app.utility.javafx.component.TableColumn;
import chigirh.app.utility.javafx.component.TableRow;
import chigirh.app.utility.javafx.component.TableRowObject;
import chigirh.app.utility.javafx.component.UtlTableCell;
import javafx.fxml.FXML;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class TablePresenterBase<E, V extends SimpleTableRow<?>, R extends TableRow<O>, O extends TableRowObject<O,V>>
		extends PresenterBase {

	private static final String STYLE_CLASS = ".table";

	@FXML
	protected VBox table;

	@FXML
	protected HBox header;

	@FXML
	protected VBox body;

	protected List<TableColumn<V, ?, ?>> columns;

	protected final List<R> tableRows = new ArrayList<>();

	/** 現在見えているTableRowObjectの先頭 */
	protected O head = null;

	/** 現在見えているTableRowObjectの最後 */
	protected O tail = null;

	/** 全てのTableRowObjectの先頭 */
	protected O first = null;

	/** 全てのTableRowObjectの最後 */
	protected O last = null;

	protected abstract void clumnDefinition();

	protected abstract List<E> getEntity();

	protected abstract O createRowObject(E entity,V vm);

	protected abstract V createVm(E entity);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table.getStyleClass().add(STYLE_CLASS);
	}

	public void update() {
		List<E> all = getEntity();
		first = null;
		last = null;
		all.stream().forEach(this::createRow);
		head = first;
		redraw();
	}

	protected void createRow(E entity) {
		V vm = createVm(entity);
		O rowObject = createRowObject(entity,vm);
		rowObject.setRowFactory(() -> createRow(entity, vm));

		if (last == null) {
			first = rowObject;
			last = rowObject;
		} else {
			rowObject.setPrev(last);
			last.setNext(rowObject);
			last = rowObject;
		}

	}

	protected HBox createRow(E entity, V vm) {
		HBox row = new HBox();
		row.getChildren().addAll(columns.stream().map(e -> createCell(e, entity, vm)).collect(Collectors.toList()));

		return row;
	}

	protected UtlTableCell<?, ?> createCell(TableColumn<V, ?, ?> column, E entity,
			V vm) {
		UtlTableCell<?, ?> cell = column.cellCreate(vm);
		return cell;
	}

	public void redraw() {
		tableRows.forEach(R::clear);
		if (head == null) {
			return;
		}
		O sequence = head;

		for (R tableRow : tableRows) {

			while (sequence != null) {
				if (sequence.isVisible()) {
					tableRow.set(sequence);
					sequence.setDisplay(true);
					tail = sequence;
					sequence = sequence.next();
					break;
				}
				sequence.setDisplay(false);
				sequence = sequence.next();

			}

		}
	}

	protected String longToDateTime(Long longDate) {
		if (longDate == null) {
			return StringUtils.EMPTY;
		}
		Date date = new Date(longDate);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
		return sdf.format(date);
	}

	protected String longToDate(Long longDate) {
		if (longDate == null) {
			return StringUtils.EMPTY;
		}
		Date date = new Date(longDate);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/*
	 * スクロール処理ここから
	 */

	protected void onScroll(ScrollEvent e) {

		if (head == null) {
			return;
		}

		double deltaY = e.getDeltaY();

		if (deltaY < 0) {
			scrollDown();
		}
		if (0 < deltaY) {
			scrollUp();
		}

		redraw();

	}

	private void scrollUp() {

		O sequence = head.prev();

		while (sequence != null) {
			if (sequence.isVisible()) {
				head = sequence;
				break;
			}
			sequence = sequence.prev();
		}
	}

	private void scrollDown() {

		O tailSequence = tail.next();

		while (tailSequence != null) {
			if (tailSequence.isVisible()) {
				O headSequence = head.next();
				while (headSequence != null) {
					if (headSequence.isVisible()) {
						head = headSequence;
						break;
					}
					headSequence = headSequence.next();
				}
				break;
			}
			tailSequence = tailSequence.prev();
		}

	}

	/*
	 * スクロール処理ここまで
	 */

}
