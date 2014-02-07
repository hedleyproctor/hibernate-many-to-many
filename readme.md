Hibernate Many-to-Many example
==============================

This code demonstrates three ways of modelling a many-to-many association with Hibernate / JPA:

1. Using a join table that is not mapped as either an entity or an embedded component type.
2. Mapping the join table as an entity in its own right, so you can add properties to it.
3. Using a collection of embedded components, to simplify the management of the persistence of the association.

The code is written using TestNG and an in-memory HSQL database. It is built with Maven, so if you have Maven installed, you can run the three tests with:

    mvn test