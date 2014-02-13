package com.github.ddth.commons.utils;

import java.util.regex.Pattern;

/**
 * Version string utilities.
 * 
 * @author Thanh Ba Nguyen <bnguyen2k@gmail.com>
 * @since 0.1.1
 * @see <code>http://stackoverflow.com/questions/198431/how-do-you-compare-two-version-strings-in-java</code>
 */
public class VersionUtils {
	/**
	 * Compares two version strings.
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int compareVersions(String v1, String v2) {
		String s1 = normalisedVersion(v1);
		String s2 = normalisedVersion(v2);
		return s1 != null ? (s2 != null ? s1.compareTo(s2) : 1)
				: (s2 != null ? -1 : 0);
	}

	/**
	 * Normalizes a version string with default separator and max width.
	 * 
	 * @param version
	 * @return
	 */
	public static String normalisedVersion(String version) {
		return normalisedVersion(version, ".", 4);
	}

	/**
	 * Normalizes a version string.
	 * 
	 * @param version
	 * @param sep
	 * @param maxWidth
	 * @return
	 */
	public static String normalisedVersion(String version, String sep,
			int maxWidth) {
		if (version == null) {
			return null;
		}
		String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
		StringBuilder sb = new StringBuilder();
		for (String s : split) {
			sb.append(String.format("%" + maxWidth + 's', s));
		}
		return sb.toString();
	}
}
