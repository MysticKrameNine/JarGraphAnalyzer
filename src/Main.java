import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
	
	public static String JAR_NAME = "JavaIntrospector.jar";
	public static void main(String[] args) {
		
		try {
			// Convertir le chemin en URL
			File classesDir = new File(JAR_NAME);
			URL url = classesDir.toURI().toURL();
			System.out.println("url:" + url);
			
			// Scan le jar pour renvoyer les noms des classes
			List<String> classNameList = JarScanner.scanJar("jar/" + JAR_NAME);
			
			try {
				List<Class<?>> classList = classNameList.stream().map(JarScanner::loadClass).collect(Collectors.toList());
				for ()
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			// Charger les classes à partir du répertoire spécifié
			
//			Path path = Paths.get("../").toAbsolutePath();
//			System.out.println("Path: " + path);
			
			// Afficher les classes présentes dans le JAR
//			System.out.println("Classes trouvées dans le JAR :");
//			for (String className : classMap.keySet()) {
//				System.out.println(" - " + className);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}