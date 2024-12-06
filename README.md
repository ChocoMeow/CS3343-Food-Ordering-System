# Real-Time Food Ordering System for Robotic Restaurant

## Overview

The **Real-Time Food Ordering System for Robotic Restaurant** aims to enhance operational efficiency and customer experience through automation and real-time tracking. This project implements a queuing algorithm to optimize order processing and integrates real-time tracking features to keep customers informed. Thorough testing and evaluation of the system ensure usability and performance.

## Features

- Queuing algorithm for optimized order processing
- Real-time order tracking for customers
- User-friendly interface
- Comprehensive testing for usability and performance

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 17 or higher
- **Apache Maven**: Version 3.8 or higher

### Installing Java

1. **Download JDK**: You can download the latest version of the JDK from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [AdoptOpenJDK](https://adoptopenjdk.net/).
   
2. **Install JDK**: Follow the installation instructions for your operating system.

3. **Set JAVA_HOME**:
   - On Windows:
     1. Right-click on 'This PC' and select 'Properties'.
     2. Click on 'Advanced system settings'.
     3. Click on 'Environment Variables'.
     4. Under 'System Variables', click 'New' and add:
        - Variable name: `JAVA_HOME`
        - Variable value: `C:\path\to\your\jdk`
   - On macOS/Linux:
     - Add the following line to your `.bash_profile`, `.bashrc`, or `.zshrc` file:
       ```bash
       export JAVA_HOME=/path/to/your/jdk
       ```

### Installing Maven

1. **Download Maven**: Visit the [Apache Maven website](https://maven.apache.org/download.cgi) and download the latest binary zip archive.

2. **Install Maven**:
   - Extract the downloaded archive to a directory on your system.
   - Add the `bin` directory of the extracted folder to your system's PATH.

3. **Verify Installation**:
   - Run the following commands in your terminal or command prompt:
     ```bash
     java -version
     mvn -version
     ```

## Building the Project

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/real-time-food-ordering-system.git
   cd real-time-food-ordering-system
   ```

2. **Build the Project**:
   ```bash
   mvn clean install
   OR
   mvn clean install -DskipTests # Bypass until tests
   ```

3. **Run the Application:**:
   ```
   java -jar target\food-ordering-system-1.0-SNAPSHOT.jar 
   ```

## Contributing
Contributions are welcome! Please feel free to submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments
- Special thanks to all contributors and open-source projects that made this possible.
