# JarGraphAnalyzer


JavaIntrospector is a tool for analyzing Java Archive (JAR) files, extracting key information about classes, attributes, and methods. The tool outputs a JSON file that enables the creation of a knowledge graph, which can be visualized using Neo4j.

## Features

- **Class Exploration:** Given a JAR file, JavaIntrospector identifies all classes, providing a comprehensive view of the code structure.
  
- **Attribute and Method Extraction:** JavaIntrospector goes beyond class identification, extracting detailed information about attributes and methods within each class.
  
- **Graph Visualization:** The tool outputs a JSON file that serves as input for creating a knowledge graph. Utilizing Neo4j, you can visually explore the relationships between different elements of your Java code.

## Usage

```bash
java -jar JavaIntrospector.jar -input your_file.jar -output output.json
