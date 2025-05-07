Linear search:
Show initial pseudocode
Handle the smaller screen


Linear search:
Array Cell color single source
Array Border color single source
Array Element color single source
Array element text dymanic based of cell color
Array pointer color dymaic
Pesuodcolor text size automatiac
Pesudocode text color single source
when algorithms finished disable the next so that user can understand it
once reset the pseudocode and other should be clear with state clear
if already finished then show a friendly message

code viewer  design with font changeable
# Code Viewer
Look Like a Viewer such as Theme, Border, Copy
Text size should changeable
Code should be copied

#Define a global theme start it all place,
later can provide own theme for each if need to ovveride
support different theme


## convention
name with _ used to use internal such asp package _ should not be used by outer module

# Bug
Prime and Tree drawing new edge [bug]
Why edge is not unselected or try to use a method for unselected
Since under the hood using the graph editor in the Tre editor that means problem is in the
Graph editor
IN case of tree the state is not saved means the new node can not added
------------
Make the edge selection easier with a tools or swipe because edge are less thin and takes times to select it
Or show the controll point with click-able so that can select a edge if a controll point is clicked 

In case of edge drawing why the edge are not drawing correcly?
show a state that edge are connected,
Why adding new graph or node is not working???



# Next TODO:
Theme Fix and Color fix

Expression Tree:
Simulate the calculation result of the tree by replacing the tree


## Implementing the Pseudocode Executor
- Since the Iterator or Sequence builder will execute the code,so there is no alternative to 
update code state within there, we tried to use the algo or simulation state to use update pseudo
but they are  not synced,for more details visit the issues:
- 

 ### Feature
 -  Each Line of code can be highlight able and each word can be color able so that is  look like code editor
   - That is why use Annotated String instead of Raw String and File to store pseudocode
- It will show the status of current variable along with the line ,as shown in IntelliJ IDEA debugger 
### Proposed Solution
The Executor will need for each of the algorithms,that is why we have to find a better solution so that 
we can reuse it easily.
Right now the idea is as Follow
- We will define a Pseudocode executor class,that will observe the variables that is used in this pseudocode
where the variable should be primitive such as String and it  nullable because it is possible that at 
certain point of time a variable is not active or dead
- This will reduce the other class responsibility,because the client class now have to pass the state 
to that class and the class will automatically show the variable state
- We need to use line number explicitly when generation the pseudocode,since the code is nothing but a 
list of line of code,so using the index we can find the line number,if needed.
- We can manually manage the indention
- some line may need a line break means new line from previous line so that the code is more readable,
- we can do that using new line character

- Should we use abstraction while implementing?
  - Yes we should,the client care only about the Pseudocode, and a function for highLight a line 
  - 

## Caution
the Algo or Simulation state is used for visualize,so there may be delay in the state,so if we direcly depends on these state
then we may not sync the code executor and the properly in that case we should depends at less as possible in that state...
because we the pseudocode is direcly couped with the visual state then if visual state needed to be delayed for better visual representation,
in that case the pseudocode will not synce properly

the better solution is to use separate state for them and the iterator or the sequention builder will update both the visual and the algo state
because the iterator or the sequence builder is executing the code.




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