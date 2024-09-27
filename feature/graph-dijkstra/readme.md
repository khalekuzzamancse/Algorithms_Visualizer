Resources
https://youtu.be/QLUCdAURRzI?list=PL6KMWPQP_DM8t5pQmuLlarpmVc47DVXWd


GraphEditorControllerImplLog::: :-> [(A,B), (A,E), (D,C), (C,D), (B,E), (E,B), (E,C), (D,A)]
- What are the possible state that will change?
* - Edge cost/weight  is fixed
* - All nodes are fixed
* - Distance will be updated
- On start initial the distance will with ∞ off all node, the 0 for the source that means
 we have to change once or more nodes distance so one of the state is to pass, instead of 
pass the nodes we can pass their id only because the remaining are the same
the set of nodes ,but if we pass the id then we have to pass the updated distance also
that is why it is better to pass the nodes itself
- Via which node updating the distance means need to know the processing node
- Via which edge updating the distance means need to know the processing edge
- Force to edge weight be positive because -ve weight will not work here
- For any node the edge will minimum cost will be used to relax or update so you can 
get the minimum cost end
- Graph must be directed and weighted with positive values