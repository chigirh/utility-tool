package chigirh.app.utility.javafx.presenter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import chigirh.app.utility.javafx.component.table.SimpleTableRow;
import chigirh.app.utility.javafx.component.table.TableCell;
import chigirh.app.utility.javafx.component.table.TableColumn;
import chigirh.app.utility.javafx.component.table.TableRow;
import chigirh.app.utility.javafx.component.table.TableRowObject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import lombok.Getter;

public abstract class TablePresenterBase<E, V extends SimpleTableRow<?>, R extends TableRow<O>, O extends TableRowObject<O, V>>
		extends PresenterBase {

	private static final String STYLE_CLASS = "table";

	@FXML
	protected VBox table;

	@FXML
	protected HBox header;

	@FXML
	protected VBox body;

	protected List<TableColumn<V, ?, ?>> columns;

	protected final List<R> tableRows = new ArrayList<>();

	@Getter
	protected final List<O> tableRowObjects = new ArrayList<>();

	/** 現在見えているTableRowObjectの先頭 */
	protected O first = null;

	/** 現在見えているTableRowObjectの最後 */
	protected O last = null;

	/** 全てのTableRowObjectの先頭 */
	protected O head = null;

	/** 全てのTableRowObjectの最後 */
	protected O tail = null;

	protected abstract int getRowCount();

	protected abstract void clumnDefinition();

	protected abstract R getRow();

	protected abstract List<E> getEntity();

	protected abstract O createRowObject(E entity, V vm);

	protected abstract V createVm(E entity);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table.getStyleClass().add(STYLE_CLASS);
		clumnDefinition();
		createHeaderBefore();
		createHeader();
		createHeaderAfter();
		table.setOnScroll(this::onScroll);

		for (int i = 0; i < getRowCount(); i++) {
			R tableRow = getRow();
			tableRows.add(tableRow);
		}

		body.getChildren().addAll(tableRows);
	}

	public void update() {
		List<E> all = getEntity();
		tableRowObjects.clear();
		head = null;
		tail = null;
		all.stream().forEach(this::createRow);
		defaultSort();
		relink();
		first = head;
		redraw();
	}

	public void delete() {
	}

	protected void createRow(E entity) {
		V vm = createVm(entity);
		O rowObject = createRowObject(entity, vm);
		tableRowObjects.add(rowObject);
		rowObject.setRowFactory(() -> createRow(entity, vm));

		if (tail == null) {
			head = rowObject;
			tail = rowObject;
		} else {
			rowObject.setPrev(tail);
			tail.setNext(rowObject);
			tail = rowObject;
		}

	}

	protected HBox createRow(E entity, V vm) {
		HBox row = new HBox();
		row.getChildren().addAll(columns.stream().map(e -> createCell(e, entity, vm)).collect(Collectors.toList()));

		return row;
	}

	protected TableCell<?, ?> createCell(TableColumn<V, ?, ?> column, E entity,
			V vm) {
		TableCell<?, ?> cell = column.cellCreate(vm);
		return cell;
	}

	public void relink() {

		if(CollectionUtils.isEmpty(tableRowObjects)){
			head = null;
			tail =null;
			return;
		}

		O sequence = tableRowObjects.get(0);
		sequence.setPrev(null);
		head = sequence;
		for (int idx = 0; idx < tableRowObjects.size() - 1; idx++) {
			O nextSequence = tableRowObjects.get(idx + 1);
			sequence.setNext(nextSequence);
			nextSequence.setPrev(sequence);

			sequence = nextSequence;
		}
		sequence.setNext(null);
		tail = sequence;

	}

	public void redraw() {
		tableRows.forEach(R::clear);
		if (first == null) {
			return;
		}
		O sequence = first;

		for (R tableRow : tableRows) {

			while (sequence != null) {
				if (sequence.isVisible()) {
					tableRow.set(sequence);
					sequence.setDisplay(true);
					last = sequence;
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

		if (first == null) {
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

		O sequence = first.prev();

		while (sequence != null) {
			if (sequence.isVisible()) {
				first = sequence;
				break;
			}
			sequence = sequence.prev();
		}
	}

	private void scrollDown() {

		O tailSequence = last.next();

		while (tailSequence != null) {
			if (tailSequence.isVisible()) {
				O headSequence = first.next();
				while (headSequence != null) {
					if (headSequence.isVisible()) {
						first = headSequence;
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

	/*
	 * ヘッダー、ソート処理ここまで
	 */

	private List<TableHeaderCell> headerCells = new ArrayList<>();

	public static class TableHeaderCell extends Label {

		private static final String STYLE_CLASS = "header-cell";

		private static final String SVG_PATH = "M 0 0 h 7 l -3.5 4 z";

		private boolean isSorted = false;

		private boolean isAsc = false;

		private boolean isDesc = false;

		private SVGPath arrow;

		public TableHeaderCell() {

			getStyleClass().add(STYLE_CLASS);

			arrow = new SVGPath();
			arrow.setContent("M 0 0 h 7 l -3.5 4 z");
			arrow.setFill(Color.LIGHTBLUE);
			arrow.setOpacity(0);

			setGraphic(arrow);

		}

		public void sort() {
			if (isSorted && isDesc) {
				isSorted = false;
				isDesc = false;
				arrow.setOpacity(0);
				return;
			}
			if (isSorted && isAsc) {
				isAsc = false;
				isDesc = true;
				arrow.setRotate(0);
				return;
			}
			arrow.setOpacity(100);
			arrow.setRotate(180);
			isSorted = true;
			isAsc = true;
		}

		public void clear() {
			isSorted = false;
			isAsc = false;
			isDesc = false;
			arrow.setOpacity(0);
		}

	}

	protected void defaultSort() {

	}

	protected void createHeaderBefore() {
	}

	protected void createHeaderAfter() {
	}

	protected void createHeader() {
		header.getChildren().addAll(columns.stream().map(this::createHeaderCell).collect(Collectors.toList()));
	}

	protected TableHeaderCell createHeaderCell(TableColumn<V, ?, ?> def) {
		TableHeaderCell cell = new TableHeaderCell();
		headerCells.add(cell);
		cell.setText(def.getColumnName());
		cell.setPrefWidth(def.getWidth());

		if (def.getSortCondition() != null) {
			cell.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> sort(e, def, cell));
		}

		return cell;
	}

	private void sort(MouseEvent event, TableColumn<V, ?, ?> def, TableHeaderCell cell) {
		if (event.getButton() != MouseButton.PRIMARY) {
			return;
		}
		headerCells.parallelStream().filter(e -> e != cell).filter(e -> e.isSorted).forEach(e -> e.clear());
		cell.sort();

		if (!cell.isSorted) {
			update();
			return;
		}

		tableRowObjects.sort(def.getSortCondition());
		if (cell.isDesc) {
			Collections.reverse(tableRowObjects);
		}
		relink();
		redraw();
	}

	/*
	 *ヘッダー、ソート処理ここまで
	 */

}
