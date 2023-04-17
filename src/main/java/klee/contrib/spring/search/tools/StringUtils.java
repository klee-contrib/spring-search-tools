package klee.contrib.spring.search.tools;

/**
 * @author gderuette
 *
 */
public final class StringUtils {
	private StringUtils() {
	}

	/**
	 * On abaisse la premiere lettre.
	 * 
	 * @param strValue String non null
	 * @return Chaine avec la premiere lettre en minuscule
	 */
	public static String first2LowerCase(final String strValue) {
		// -----
		if (strValue.isEmpty()) {
			return strValue;
		}

		final char firstChar = strValue.charAt(0);
		if (Character.isUpperCase(firstChar)) { // la méthode est appellé souvent et la concaténation de chaine est
												// lourde : on test avant de faire l'opération
			return Character.toLowerCase(firstChar) + strValue.substring(1);
		}
		return strValue;
	}

	/**
	 * Capitalisation de la premiÃ¨re lettre.
	 *
	 * @param strValue String non null
	 * @return Chaine avec la premiere lettre en majuscule
	 */
	public static String first2UpperCase(final String strValue) {

		if (strValue.isEmpty()) {
			return strValue;
		}

		final char firstChar = strValue.charAt(0);
		if (Character.isLowerCase(firstChar)) { // la mÃ©thode est appellÃ© souvant et la concatÃ©nation de chaine est
												// lourde : on test avant de faire l'opÃ©ration
			return Character.toUpperCase(firstChar) + strValue.substring(1);
		}
		return strValue;
	}

	/**
	 * XXX_YYY_ZZZ -> xxxYyyZzz.
	 * 
	 * @param str la chaine de caratÃ©res sur laquelle s'appliquent les
	 *            transformation
	 * @return camelCase
	 */
	public static String constToLowerCamelCase(final String str) {
		return constToCamelCase(str, false);
	}

	/**
	 * XXX_YYY_ZZZ -> XxxYyyZzz.
	 * 
	 * @param str la chaine de caratÃ©res sur laquelle s'appliquent les
	 *            transformation
	 * @return CamelCase
	 */
	public static String constToUpperCamelCase(final String str) {
		return constToCamelCase(str, true);
	}

	/**
	 * XXX_YYY_ZZZ -> XxxYyyZzz ou xxxYyyZzz.
	 * 
	 * @param str             la chaine de caratÃ©res sur laquelle s'appliquent les
	 *                        transformation
	 * @param first2UpperCase dÃ©finit si la premiÃ¨re lettre est en majuscules
	 * @return Renvoie une chaine de caratÃ©re correspondant Ã  str en minuscule et
	 *         sans underscores, Ã  l'exception des premiÃ¨res lettres aprÃ©s les
	 *         underscores dans str
	 */
	private static String constToCamelCase(final String str, final boolean first2UpperCase) {
		// -----
		final StringBuilder result = new StringBuilder();
		boolean upper = first2UpperCase;
		Boolean digit = null;
		final int length = str.length();
		char c;
		for (int i = 0; i < length; i++) {
			c = str.charAt(i);
			if (c == '_') {
				if (digit != null && digit && Character.isDigit(str.charAt(i + 1))) {
					result.append('_');
				}
				digit = null;
				upper = true;
			} else {
				digit = Character.isDigit(c);

				if (upper) {
					result.append(Character.toUpperCase(c));
					upper = false;
				} else {
					result.append(Character.toLowerCase(c));
				}
			}
		}
		return result.toString();
	}

	public static String camelToConstCase(final String str) {
		// -----
		final StringBuilder result = new StringBuilder();
		final int length = str.length();
		char c;
		boolean isDigit = false;
		for (int i = 0; i < length; i++) {
			c = str.charAt(i);
			if (Character.isDigit(c) || c == '_') {
				if (i > 0 && !isDigit) {
					result.append('_');
				}
				isDigit = true;
			} else if (Character.isUpperCase(c)) {
				if (i > 0) {
					result.append('_');
				}
				isDigit = false;
			} else {
				isDigit = false;
			}
			result.append(Character.toUpperCase(c));
		}
		return result.toString();
	}
}
