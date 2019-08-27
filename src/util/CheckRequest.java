package util;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class CheckRequest {
	public CheckRequest() {
	}

	public String getEncode(String encode, String requestContent) {
		String encodeText = new String();
		try {
			encodeText = requestContent.replaceAll("%", "");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return encodeText;
		}
	}

	public static String get(String requestContent) {
		String encodeText = new String();

		try {
			encodeText = requestContent;

			if (encodeText != null) {
				encodeText = encodeText.replaceAll(" and", "");
				encodeText = encodeText.replaceAll(" ", "");
				encodeText = encodeText.replaceAll("%28", "");
				encodeText = encodeText.replaceAll("select", "");
				encodeText = encodeText.replaceAll("%22", "");
				encodeText = encodeText.replaceAll("'", "");
				encodeText = encodeText.replaceAll("--", "");
				encodeText = encodeText.replaceAll("=", "");
				encodeText = encodeText.replaceAll("char", "");
				encodeText = encodeText.replaceAll("%", "");
				
				if (encodeText.matches("([^a-zA-Z_0-9]|[a-zA-Z_0-9])*[Ss]{1}[Cc]{1}[Rr]{1}[Ii]{1}[Pp]{1}[Tt]{1}([^a-zA-Z_0-9]|[a-zA-Z_0-9])*")) {
					encodeText = encodeText.toLowerCase();
					encodeText = encodeText.replaceAll("script", "");
				} else if (encodeText.matches("([^a-zA-Z_0-9]|[a-zA-Z_0-9])*[Aa]{1}[Ll]{1}[Ee]{1}[Rr]{1}[Tt]{1}([^a-zA-Z_0-9]|[a-zA-Z_0-9])*")) {
					encodeText = encodeText.toLowerCase();
					encodeText = encodeText.replaceAll("alert", "");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return encodeText;
		}
	}
}
