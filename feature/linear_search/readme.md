entry point=LinearSearchDestination
Though we can do lazy evaluation using iterator patterns but for faster development
we are using the kotlin sequence which is under the hood use iterator pattern
If we use sequence then we have less code,the algorithm will be same as raw algorithm 
Using raw Iterator pattern it is hard to highlight the exact line of the pseudocode
that is why we are going to us the sequence.

Right now we are going to create separate module for each  algorithm,
because in future we will add the tutorials,extra thing related to algorithms.
at most we are going to implement 20 algorithms so we will have 20 modules,which is fine.

### `contract` package will define the nessary interface and models,it does not
provide any implementations.

`controllers` package will provide the implementation,there can be multiple implementations based on these contract
the UI will use the contracts ,it will not use the implementations so that later
we can change the implementation without effecting the UI

These will define the state and UI for visualization the algo with controller
#### package:`contract`
package will define the necessary interface and models,it does not
provide any implementations.

#### package:`controllers`
package will provide the implementation,there can be multiple implementations based on these contract
the UI will use the contracts ,it will not use the implementations so that later
we can change the implementation without effecting the UI
