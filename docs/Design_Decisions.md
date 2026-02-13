# Design Decisions

## Architecture Overview
MediTrack follows a layered architecture with clear separation of concerns:
- **Entity Layer**: Domain models
- **Service Layer**: Business logic
- **Util Layer**: Helper classes and utilities
- **Interface Layer**: Contracts for common behaviors

## Key Design Decisions

### 1. Generic DataStore
**Decision**: Use a generic `DataStore<T>` class for all entities

**Rationale**:
- Code reusability and DRY principle
- Type safety through generics
- Consistent CRUD operations across all entities
- Easy to extend for new entity types

**Trade-offs**:
- Uses reflection for ID extraction (slight performance overhead)
- More complex than simple entity-specific stores

### 2. Inheritance Hierarchy
**Decision**: Create `Person` as an abstract base class for `Doctor` and `Patient`

**Rationale**:
- Common attributes (id, name, age, contact) shared between doctors and patients
- Promotes code reuse
- Demonstrates OOP inheritance

**Trade-offs**:
- Tight coupling between parent and child classes
- Changes to Person affect all subclasses

### 3. Interface Segregation
**Decision**: Create separate interfaces (`Searchable`, `Payable`) instead of one large interface

**Rationale**:
- Follows Interface Segregation Principle (ISP)
- Classes implement only what they need
- Better flexibility for future extensions

**Trade-offs**:
- More files to manage
- Multiple interfaces per class in some cases

### 4. Immutable BillSummary
**Decision**: Make `BillSummary` immutable (final class, no setters)

**Rationale**:
- Thread safety without synchronization
- Prevents accidental modification of summary data
- Suitable for reporting and display purposes

**Trade-offs**:
- New object creation for any changes
- Slightly more memory usage

### 5. Service Layer Pattern
**Decision**: Separate service classes for each entity type

**Rationale**:
- Encapsulates business logic
- Makes testing easier
- Clear separation from data access
- Single Responsibility Principle

**Trade-offs**:
- More classes to maintain
- Additional layer of abstraction

### 6. Custom Exception Handling
**Decision**: Create custom exceptions (`AppointmentNotFoundException`, `InvalidDataException`)

**Rationale**:
- More descriptive error messages
- Better error handling and debugging
- Domain-specific exception types

**Trade-offs**:
- More exception classes to maintain
- Developers need to be aware of custom exceptions

### 7. Static Utility Classes
**Decision**: Use static methods in utility classes with private constructors

**Rationale**:
- No need for instantiation
- Global access to utility functions
- Memory efficient

**Trade-offs**:
- Testing can be challenging (static methods)
- Less flexibility than instance methods

### 8. AtomicInteger for ID Generation
**Decision**: Use `AtomicInteger` in `IdGenerator` for thread-safe counters

**Rationale**:
- Thread-safe without explicit synchronization
- Better performance than synchronized methods
- Atomic operations guarantee correctness

**Trade-offs**:
- Slightly more complex than simple int counters
- Assumes counter-based ID generation is sufficient

### 9. CSV for Data Persistence
**Decision**: Use CSV files for data storage (optional implementation)

**Rationale**:
- Simple and human-readable
- No external database dependencies
- Easy to debug and inspect
- Suitable for small to medium datasets

**Trade-offs**:
- Not suitable for large-scale applications
- Limited query capabilities
- No ACID guarantees
- Concurrent access challenges

### 10. No Framework Dependencies
**Decision**: Pure Java implementation without Spring, Hibernate, etc.

**Rationale**:
- Focus on core Java concepts
- Learning fundamentals
- No external dependencies
- Easy setup and deployment

**Trade-offs**:
- Manual implementation of common patterns
- More boilerplate code
- Limited features compared to frameworks