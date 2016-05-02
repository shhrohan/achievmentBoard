package com.assignment.utils;

public class PrintUtil {

	public static void print(String message) {
		System.out.print("[Message] : " + message);
	}

	public static void printInNewLine(String message) {

		System.out.println("[Message] : " + message);

	}

	public static boolean printException(Exception ex) {

		System.out.println();
		System.out.println("#################[Exception]##################");
		System.out.println("[Exception causing class] : " + ex.getClass());
		System.out.println("[Message] : " + ex.getMessage());
		System.out.println("[Localized Message] : " + ex.getLocalizedMessage());
		System.out.println("#################[######### ]##################");
		ex.printStackTrace();
		return false;
	}
}
