package de.htwg.flowchartgenerator.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
/**
 * Resource utilities for inter-bundle operations.
 * @author Aldi Alimucaj
 *
 */
public class ResourcesUtils {
	public static File getLocation(){
		URL bundleRoot = Platform.getBundle("org.flowChartPlugin").getEntry("/");
		URL fileURL;
		File file = null;
		try {
			fileURL = FileLocator.toFileURL(bundleRoot);
			file = new File(fileURL.toURI());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e3) {
			e3.printStackTrace();
		}
		return file;
	}
}
