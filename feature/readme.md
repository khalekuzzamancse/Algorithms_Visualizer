## Implementing the Pseudocode Executor
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
- 