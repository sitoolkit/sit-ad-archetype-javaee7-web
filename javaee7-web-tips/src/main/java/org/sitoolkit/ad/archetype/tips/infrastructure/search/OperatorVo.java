package org.sitoolkit.ad.archetype.tips.infrastructure.search;

/**
 * このクラスは、検索条件の比較演算子を表すVOです。
 * @author SIToolkit
 */
public enum OperatorVo {
	/**
	 * equal
	 */
	eq("="),
	/**
	 * not equal
	 */
	ne("<>"),
	/**
	 * greater than
	 */
	gt(">"),
	/**
	 * greater than equal
	 */
	ge(">="),
	/**
	 * less than
	 */
	lt("<"),
	/**
	 * less than equal
	 */
	le("<="),
	/**
	 * start with
	 */
	sw("LIKE"),
	/**
	 * in
	 */ 
	in("IN");
	
	private String str;
	
	private OperatorVo(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}
}
