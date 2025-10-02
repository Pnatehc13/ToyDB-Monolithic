ToyDB v1.0: A Monolithic Database Engine
This project is a simple, single-user, persistent database engine built from scratch in Java. It was created as a hands-on learning exercise to understand the fundamental, internal workings of a Database Management System (DBMS).

The engine is "monolithic," meaning the user interface (command prompt) and the storage engine (the database logic) are combined into a single, self-contained application.

Core Features
Full CRUD Functionality: Supports the primary database operations:

insert <id> <name> <email>

select / select where id <op> <val>

update <id> set <col>=<val>

delete where id <op> <val>

Persistence: All data is saved to a local database.db text file, ensuring durability between sessions.

High-Performance Indexing: Uses an in-memory TreeMap as its primary index. This provides:

Extremely fast equality lookups (id = ...).

Efficient, high-speed range queries (id > ... and id < ...).

Concurrency Control: The engine is thread-safe. It implements a ReentrantReadWriteLock to prevent data corruption from concurrent operations, ensuring that multiple readers can access data simultaneously while writes are handled exclusively.

How to Run
Compile: Compile all the Java source files.

javac *.java

Run: Execute the main class to start the interactive command prompt.

java Main

Use: Enter commands at the db> prompt.

Learning Journey
This project served as a deep dive into the core components of a DBMS, including:

Parsing user commands.

The architectural decision to move from a simple ArrayList to a more powerful, sorted TreeMap index.

Implementing a Readers-Writer lock to solve concurrency problems.

Refactoring a growing codebase into a clean, manageable structure.
