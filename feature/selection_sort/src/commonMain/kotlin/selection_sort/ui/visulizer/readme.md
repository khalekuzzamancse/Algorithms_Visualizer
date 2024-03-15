These will define the state and UI for visualization the algo with controller
#### package:`contract` 
package will define the necessary interface and models,it does not
provide any implementations.

#### package:`controllers`
package will provide the implementation,there can be multiple implementations based on these contract
the UI will use the contracts ,it will not use the implementations so that later
we can change the implementation without effecting the UI

## TODO
- Have to store the key at a temporary variable
- Show the shifting effect of the element position as animated state
- To do that, move the key to a cell and then while all shiting done,move the key to it destination
- 