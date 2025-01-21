# TeleportApplication

## How to Run

There are three ways to run this application from the command line:

1. Using a file as input:

   **`java -jar TeleportApplication.jar {input_file_path}`**

2. Using piped input:

   **`cat input.txt | java -jar TeleportApplication.jar`**

3. Using input redirection:

   **`java -jar ./build/libs/TeleportApplication.jar < {input_file_path}`**

## Design Overview

The application is designed around a graph structure containing cities as StringNodes and teleportation routes as links
between StringNodes.  

Each StringNode holds a 'links' Set containing the other StringNodes linked to a given StringNode.
A StringNode is used to represent a City via the StringNode name field; and comparing StringNodes boils 
down to a **StringNode.name.equals(other StringNode.name)**, as can be seen in the _StringNode.equals()_ method override.

A GraphBuilder constructs a graph with a set of CityName to CityName pairs that are produced by the InputFileParser. When 
a graph is constructed this set of CityName link pairs is used to construct the node relationships in the graph.  The graph
maintains the StringNode relationships from that point on.   A graph instance also holds a Router instance which is used 
to determine how to route between StringNodes (e.g. City).  In other words, the Router is used to navigate the graph.
Currently, there is only one Router named TeleportationRouter, which is established as the default router during graph
construction.  The Graph class provides a method to override the default router, if ever so desired in the future.

<div style="border: 1px solid #ccc; padding: 10px; border-radius: 5px;">
<strong>Note:</strong> There is a Route class which is not really used. This class was initially intended to be used to hold specific
route navigation details. This feature was not required to satisfy the use-cases per the specification; therefore, the use 
of these objects was abandoned.  The class is only maintained in case it is needed in a future enhancement, consider it
<strong><em>deprecated</em></strong> at the very least.
</div>

A GraphQuery interface represents a question to ask of the graph.  There are distinct implementations of the GraphQuery interface
for each type of question supported by the application.  Currently, only the three types of questions in the problems 
specification are supported.  (_See the **GraphQuery** hierarchy below under '**main components**'._).  

Queries provide 
the following methods:
1. prepare() - parses the raw question from the input file (e.g. _can I teleport from Oakland to Atlanta_)
2. execute(Graph graph) - executes the parsed question (query) against ths specified graph instance
3. getResult() - Simply returns the result from a previous execute() request.
**Note** - execute saves the query result for later access by getResult(), but execute() also returns the result.  A
query result
<div style="border: 1px solid #ccc; padding: 10px; border-radius: 5px;">
   <strong>Note:</strong> execute() returns the result as well as saves it for later access by getResult(). The
    response from a query is simply a string containing the specified prefix (from problem specification) concatenated 
    with the actual result from the using the router to run the specific "question" against the graph. For example, 
   <strong><em>loop possible from Oakland: yes</em></strong>.
</div>

The main components are:

- `TeleportApplication`: Main entry point, handles input parsing and query processing.
- `InputFileParser`: Parses input from file or stdin.  The output from a parse is a set of city links and a collection of queries (questions to ask the graph).
- `Graph`: Represents the teleportation network.
- `GraphBuilder`: Constructs a Graph from parsed links.
- `Router`: Used to navigate the graph during query execution.
    - `TeleportationRouter`
- `GraphQuery`: Interface representing a question to ask of the graph.
    - `CitiesFromQuery`
    - `TeleportFromQuery`
    - `LoopPossibleQuery`

### High-level Overview of Running TeleportationApplication 
(see the _How to Run_(#how-to-run) section for specifics about how to run the application from the command line.)

This section simply contains a general overview of how the application works.

The main method is responsible for:
1. obtaining the input (file or from standard input), 
2. invoking InputFileParser, 

    Two _'artifacts'_ are produced by the InputFileParser;
    1. a set of StringNode pairs, and 
    2. a collection of GraphQuery instances (one for each question contained in the input file).    


3. building the graph, 
4. processing (executing) the queries and collecting query execution results, and
5. sending the query execution results to the console (standard out)


## Tests

There is test coverage for most of the components.  Note that most of the coverage are positive rather than negative tests (a deficiency that should be addressed in the future). 

For reference, the test class hierarchy is shown here:

- queries/
  - **GraphQueryTest**
  - **TestRunAllQueries**
- **BaseGraphTest** - Inherited by _GraphQueryTest_, _GraphTest_, and _InputFileParserTest_
- **GraphTest**
- **InputFileParserTest**
- **NodeTest** 

**_BaseGraphTest_** sets up some mock test data for subclass tests.  The mock data matches, exactly, the Example input from the specification
**_NodeTest_** tests basic node functionality; nodes can't map/link themselves, equality is name field (city name) equality.
**_GraphQueryTest_** tests all three GraphQuery types (all three specified question types) against the base provided mock data.
**_InputFileParserTest_** tests parsing functionality using the base provided mock input data.
**_TestRunAllQueries_** tests a full application run using a testinput.txt file and validates against expected results that are loaded from a testoutput.txt file

    The testinput.txt and testoutput.txt files are under the test/resources folder

#### Developer Note Regarding Testing
The tests were built alongside component development.  Taking this approach allowed me to better understand how components should be designed/implemented, as well as discover bugs early on in the development process. 
I could flesh out the test coverage in probably all test classes to cover various edge or anticipated error cases, but I left this to a future enhancement as I ran out of time.

All tests are unit tests, except for the TestRunAllQueries test class; this class is a functional test that covers input-to-output functionality similar to what is performed by the TestApplication class.

## Assumptions/Observations

- Input format is consistent (city names don't contain hyphens, queries follow specific patterns all of which are derived from the input and output examples in the specification).
- City names are case-sensitive; and cities are represented in the graph as StringNode instances where the node name field is set to the city name.
- The graph is undirected (if A can teleport to B, B can teleport to A).
- There are no duplicate links in the input but rather than using sets, lookup maps are used to enforce no duplicates as this fit better how the graph was being navigated.

## Possible Improvements

1. Implement more sophisticated error handling and user feedback.  For simplicity, all "errors" are thrown as RuntimeExceptions or derivations thereof.  Runtime exceptions avoid the interface specification forced by checked exceptions.
2. Add support for weighted edges (e.g., teleportation cost or time).  This would require a "link object" rather than just a StringNode in a collection.
3. Implement additional query types (e.g., shortest path between cities).
4. Optimize graph traversal algorithms for larger datasets; or just optimizations of the current depth-first-search approach used and reused almost exclusively. (Only need to replace with a new Router to improve the navigation.)
5. Eliminate use of recursion
6. Defined and enforce a maximum number of hops, especially if continue to use recursion.  ('would have to enforce/check on parsing/graph building, as well as during traversal)
5. Modify the TestApplication to accept user questions until some defined 'stop' indication from the user.  (A more direct Q&A interface with the application.)

## Lessons Learned

1. I started out attempting to design something more generic and flexible.  Instead, given the time constraints, I should have designed specifically for the specification.  The specified problem and use-cases is simple enough to justify this prototype style of design approach.  Going forward, we could iterate on the initial design to improve and enhance in order to be far more flexible and generic.
2. Time constraints also forced me to skimp on error handling, good comments and documentation, variable and method and class and package names, and test coverage.  In a real world situation we could have addressed this particular area of concern during daily standups and sprint planning (ideally, anyway).
