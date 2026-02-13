# JVM Report

## Overview
This document explains the JVM concepts used in the MediTrack project.

## Memory Management

### Heap Memory
- **Entity Objects**: All Doctor, Patient, Appointment, and Bill objects are stored in heap memory
- **Collections**: HashMap in DataStore and ArrayList for collections are allocated on the heap
- **Garbage Collection**: Unused objects are automatically collected by the JVM's garbage collector

### Stack Memory
- **Method Calls**: Local variables and method parameters are stored in the stack
- **Primitive Types**: Counters in IdGenerator use stack memory for primitive operations
- **Thread Safety**: Each thread has its own stack, ensuring thread-safe execution

## Object-Oriented Features

### Inheritance
- `Person` is the base class
- `Doctor` and `Patient` extend `Person`
- Demonstrates IS-A relationship

### Polymorphism
- Method overriding in `toString()` methods
- Interface implementation (Searchable, Payable)
- Runtime polymorphism through interfaces

### Encapsulation
- Private fields with public getters/setters
- Data hiding through access modifiers
- Package-private access where appropriate

### Abstraction
- Abstract `Person` class
- Interfaces: `Searchable` and `Payable`
- Service layer abstraction

## Generics
- `DataStore<T>` uses Java generics for type safety
- Type-safe collections throughout the application
- Compile-time type checking

## Exception Handling
- Custom exceptions: `AppointmentNotFoundException`, `InvalidDataException`
- Checked exceptions for critical operations
- Try-catch blocks in utility classes

## Concurrency
- `AtomicInteger` in IdGenerator for thread-safe ID generation
- Thread-safe operations without explicit synchronization
- Immutable BillSummary class for thread safety

## Best Practices
1. Immutable objects where appropriate (BillSummary)
2. Defensive copying in collections
3. Proper resource management in I/O operations
4. Use of final keyword for constants and immutable classes
