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


## Dependencies Graph
- `ui.route` define the public api to access the module for client module
- `ui.visualizer` define the UI and the logic for visualization the algorithm
- `ui.visualizer.components` - define the UI component of the visualization such as array section,pseudo execution ui,so it define the part of the UI  of that visualizer

- `domain` -define the abstract contracts such as the states of the visualizer,the controller function and the pseudocode of the algorithms ,and the other abstract thing

- `infrastructure` - it provide the implementation of that defined in the `domain` ,define the controller or the iterator for the visualization,it does not contain any UI references or UI logic,so the `data` will give the implementation of that are business logic related and defined in the`domain` ,it is equivalent the `data` layer
`ui` will give the implementation of that defined in the `domain` are related to UI and some extra UI component