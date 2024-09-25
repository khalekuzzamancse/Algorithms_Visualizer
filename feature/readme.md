
# Next TODO:
Djkstra shortest path
Tree in order, pre order  and post order ,Heap
Counting  sort,






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



