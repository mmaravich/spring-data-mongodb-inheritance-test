# What is this?

This repo is a very simple example of how inheritance support could be added to Spring Data for MongoDB.

**Please note, this code only solves inheritance in my use-case!** This is why inheritance in general is not part of
Spring Data for MongoDB as such. It is difficult to provide a solution that would work for all possible use-cases.

## My use-case

These are the rules I followed in my code:

* All entity classes for which a repository interface is to de declared have a ```@Document``` annotation. 
* All entity classes that share a common superclass **are stored in the same collection**.
* The shared common superclasses are usually abstract classes. They don't have to be, but usually are.
* To support inheritance, all subclasses (concrete classes) use ```@TypeAlias``` annotation to specify a specific 
marker used to identify class type once stored in the database. See below why I use that annotation!

Following these rules, here is the functionality I was looking for:

* Superclass repositories should work on all data within a collection. 
* Subclass repositories should only work on data that are of the subclass type - meaning that a condition involving
the ```_class``` field should be automatically added to all queries before they reach MongoDB.

The code in this repository showcases a simple implementation that achieves this. There is room for improvement, 
but this should be a nice starter for further experimentation.

## Why use @TypeAlias ?

By default Spring Data for MongoDB will put a fully qualified class name into the ```_class``` field of the entity
class (the field is added to the model automatically by Spring). This may seem fine, however if your code goes
through any refactoring where package or class names change, you will be stuck with a lot of documents inside
MongoDB with "legacy" class names. I don't really like that, so I always use the ```@TypeAlias``` annotation 
with some identifier that makes sense to me. This way any refactoring I do will not affect inheritance in any way. And 
I can also use a short identifier, because a full class name can get pretty lengthy sometimes.
