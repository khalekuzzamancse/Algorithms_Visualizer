
NEXT TODO:
- Prevent user to add node with empty label
- Take input about the graph has weighed or not,if not weighted then do not show edge cost dialogue


Todo:
- Each node size can be different,before drawing the node,it is not possible to tell it exact size
- We need each node size and the topLeft
- Since the node are square internally,so using size and top left we can calculate it boundary
- Once the  boundary is calculated,we can find an edge end point is inside a node or not
- then we can build the set of edges as Edge(nodeU,nodeV)
- then using the set of nodes and set of edges we can build the graph

TODO:
- OnTap a node is adding,what the node size and the initial position will be depends on the tap position and the text measurement
but before tapping we inputted the text of that node,
- onAddNodeRequest() ,here we can decide the size of the node ,by measuring the text size



- Create add a new node to that list,when user added it to graph
- A node can have the function called isWithinBoundary(offset)

# Implementation Guide TODO(Refactor)
- 
- Use DI that is why only depends on abstraction
- Right now need not using automatic DI,that is why use manual DI container with factory method to create object
- Since Following DI so all class are testable as well as anytime can introduce automatic DI container without changing the code