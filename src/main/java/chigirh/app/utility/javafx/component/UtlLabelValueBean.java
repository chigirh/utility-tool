package chigirh.app.utility.javafx.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtlLabelValueBean<E> {

	private String label;

	private E value;

}
