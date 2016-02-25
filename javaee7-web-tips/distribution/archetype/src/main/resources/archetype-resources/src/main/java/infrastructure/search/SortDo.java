#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.search;

/**
 * このクラスは、検索結果のソート順を格納するためのDOです。
 * @author SIToolkit
 */
public class SortDo {
	private String field;

	private boolean asc;

	public SortDo() {
	}

	public SortDo(String field, boolean asc) {
		this.field = field;
		this.asc = asc;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
