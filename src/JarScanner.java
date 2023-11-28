import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarScanner {
	public static ArrayList<String> scanJar(String jarFilePath) {
		ArrayList<String> classList = new ArrayList<>();
		
		try (JarFile jarFile = new JarFile(new File(jarFilePath))) {
			Enumeration<JarEntry> entries = jarFile.entries();
			
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.getName().endsWith(".class")) {
					
					// Récupère le nom complet de la classe
					String className = entry.getName().replace("/", ".").replace(".class", "");
					
					System.out.println(className);
					classList.add(className);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return classList;
	}
	
	public static Class<?> loadClass(String className) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading class: " + className);
		}
		return clazz;
	}
}