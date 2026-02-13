# MediTrack - Healthcare Management System

A comprehensive Java-based healthcare management system demonstrating advanced OOP concepts, design patterns, and Java 8+ features.

## 🎯 Project Highlights

### Core Features Implemented

✅ **Complete OOP Implementation**
- Inheritance (Person → Doctor/Patient)
- Polymorphism (Method overloading & overriding)
- Encapsulation (Private fields with getters/setters)
- Abstraction (Abstract MedicalEntity class, Interfaces)

✅ **Advanced OOP Features**
- **Deep Copy**: Cloneable implementation for Patient & Appointment
- **Immutable Class**: BillSummary (final class, no setters, thread-safe)
- **Enums**: Specialization, AppointmentStatus, BillType
- **Static Blocks**: Initialization in Constants & IdGenerator

✅ **Design Patterns**
- **Singleton**: IdGenerator (both eager and lazy initialization)
- **Factory**: BillFactory for creating different bill types
- **Generic Programming**: DataStore<T> for type-safe storage

✅ **Java 8+ Features**
- **Streams & Lambdas**: Extensive use in analytics
- **Optional**: Safe handling of nullable values
- **Functional Interfaces**: Comparator, Predicate usage

✅ **File I/O & Persistence**
- CSV reading/writing with try-with-resources
- Command-line argument support (--loadData)
- Automatic data persistence

## 🚀 Quick Start

### Compilation
```bash
javac -d bin -sourcepath src/main/java src/main/java/com/airtribe/meditrack/**/*.java
```

### Run Application
```bash
# Normal start
java -cp bin com.airtribe.meditrack.Main

# Start with CSV data loading
java -cp bin com.airtribe.meditrack.Main --loadData
```

### Run Tests
```bash
java -cp bin com.airtribe.meditrack.test.TestRunner
```

## 🎮 Main Menu Options

1. **Doctor Management** - Add, view, search, update, delete doctors
2. **Patient Management** - Full patient CRUD with search
3. **Appointment Management** - Schedule and manage appointments
4. **Billing Management** - Generate bills using Factory pattern
5. **Analytics & Reports** - Streams/Lambdas demonstrations
6. **Demonstrate Features** - Show all advanced features
7. **Save Data to CSV** - Persist data

## 📋 Key Features

### Enums (3 types)
- Specialization (10 types)
- AppointmentStatus (6 statuses)
- BillType (5 types)

### Deep Copy (Cloneable)
Patient and Appointment classes implement deep copy

### Singleton Pattern
IdGenerator with both eager and lazy initialization

### Factory Pattern
BillFactory creates different bill types

### Method Overloading
- PatientService: 4 overloaded searchPatient methods
- Bill: 3 overloaded calculateTotal methods

### Streams & Lambdas
Extensive use in DoctorService and AppointmentService for analytics

## 📊 File Count

- **28 Java files** across all packages
- Complete documentation in docs/

## 🏆 All Requirements Met

✅ Environment Setup & JVM Understanding
✅ Package Structure & Java Basics  
✅ Core OOP Implementation
✅ Advanced OOP (Deep copy, Immutable, Enums, Static blocks)
✅ Application Logic with Console UI
✅ 4 Bonus Features (File I/O, Design Patterns, AI, Streams)

See full documentation in docs/ folder!
