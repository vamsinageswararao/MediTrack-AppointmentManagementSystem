# MediTrack Setup Instructions

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- A text editor or IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)
- Command line terminal

## Project Structure
```
meditrack/
├── src/main/java/com/airtribe/meditrack/
│   ├── Main.java
│   ├── constants/
│   ├── entity/
│   ├── service/
│   ├── util/
│   ├── exception/
│   ├── interface/
│   └── test/
└── docs/
```

## Setup Steps

### 1. Verify Java Installation
```bash
java -version
javac -version
```

### 2. Navigate to Project Directory
```bash
cd meditrack
```

### 3. Compile the Project
```bash
# Compile all Java files
javac -d bin -sourcepath src/main/java src/main/java/com/airtribe/meditrack/**/*.java
```

### 4. Run the Main Application
```bash
java -cp bin com.airtribe.meditrack.Main
```

### 5. Run Tests
```bash
java -cp bin com.airtribe.meditrack.test.TestRunner
```

## IDE Setup

### IntelliJ IDEA
1. Open IntelliJ IDEA
2. Select "Open" and choose the `meditrack` folder
3. Wait for indexing to complete
4. Right-click on `Main.java` and select "Run Main.main()"

### Eclipse
1. Open Eclipse
2. File → Import → Existing Projects into Workspace
3. Select the `meditrack` folder
4. Right-click on `Main.java` → Run As → Java Application

### VS Code
1. Install Java Extension Pack
2. Open the `meditrack` folder
3. Press F5 to run or use the Run button

## Creating Data Directory
```bash
# Create data directory for CSV files
mkdir -p data
```

## Common Issues

### Issue: ClassNotFoundException
**Solution**: Ensure you're in the correct directory and the classpath is set properly

### Issue: Compilation Errors
**Solution**: Check Java version compatibility and ensure all dependencies are available

### Issue: File Not Found (CSV files)
**Solution**: Create the `data` directory and ensure proper file paths

## Running Individual Tests
```bash
# Compile test file
javac -d bin -cp bin -sourcepath src/main/java src/main/java/com/airtribe/meditrack/test/TestRunner.java

# Run test
java -cp bin com.airtribe.meditrack.test.TestRunner
```

## Next Steps
1. Review the code structure
2. Implement additional features
3. Add more test cases
4. Create CSV data files for persistence
5. Explore JVM concepts in JVM_Report.md
