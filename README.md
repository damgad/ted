TED - Task Executing Distributor
================================

Description
-----------

It's a small application which runs multiple tasks. A Task is an object which implements Runnable interface. TED manages multiple threads and queues tasks if required.

To communicate (add new task, get information about number of running/scheduled tasks) TED uses HTTP protocol:

* `GET /tasks/schedule?class=fully.qualified.class.Name` - schedules new task
* `GET /tasks/count-scheduled` - returns current number of scheduled tasks 
* `GET /tasks/count-running` - returns current number of running tasks

Building and running
--------------------

After cloning the repository you can use [SBT (Simple Build Tool)](http://www.scala-sbt.org/) to build and run application. In the TED's main directory:

    $ sbt run -Dhttp.port=8330
    
where 8330 is number of TCP/IP port on which TED will be listening.

To run the test go to the project's directory and:

    $ sbt test

Example
-------

There are already two examples tasks with fully qualified class names:

* `example.Itemize` which prints every second one of the 5 popular JVM languages
* `example.Calculate` which simulates calculations (long loop which does nothing)

For examlpe - to schedule the task `Itemize` run:

    $ wget http://localhost:8330/tasks/schedule?class=example.Itemize

when TED is running. The 5 JVM languages will be printed in TED's console.

Number of threads
-----------------

During the tests I realized that optimal number of running threads depends on the task type. For *Itemize* it was few thousand threads and for *Calculate* only tens (on my i7 machine).
Maximal number of running threads is preset to 100. This constant can be modified in `app/models/Scheduler.scala` source file.