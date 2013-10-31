package de.htwg.flowchartgenerator.utils;

import org.eclipse.swt.graphics.Color;
/**
 * Statics
 * @author Aldi
 *
 */
public final class Statics {
	
	public final static String EXTENTION = ".ff3";
	public final static String CFG_DIR = "/cfg";
	public final static String SEPARATOR = System.getProperty("file.separator");
	public final static String EDITOR_NAME = "Flow Chart Editor";
	public static int MACCABE_MAX_RES = 7;
	public static final int NODE_MAX_TEXT_LENGTH = 5;
	public static final String NODE_DEFAULT_TEXT = "Expression";
	public final static Color WHITE = new Color(null, 255, 255, 255);
	public final static Color GRAY = new Color(null, 204, 204, 204);
	public static final Color H_COLOR = new Color(null, 255, 140, 0);
	public static final Color RED_COLOR = new Color(null, 200, 20, 20);
	public static final Color GREEN_COLOR = new Color(null, 80, 250, 90);
	
	private Statics(){
	}
}
