package de.htwg.flowchartgenerator.utils;

import org.eclipse.core.resources.ResourcesPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The bundle checking class
 * 
 * @author Aldi Alimucaj
 * 
 */
public class BundleChecker {
	public static final String CODE_COVER = "org.codecover.eclipse";
	static boolean codeCover = false;

	/**
	 * 
	 * @return true if the required plugin was found.
	 */

	public static boolean hasCodeCover() {
		BundleContext bc = ResourcesPlugin.getPlugin().getBundle().getBundleContext();
		Bundle[] many = bc.getBundles();
		for (int i = 0; i < many.length; i++) {
			if (many[i].getSymbolicName().equals(CODE_COVER)) {
				codeCover = true;
				break;
			}
		}
		return codeCover;
	}

}
