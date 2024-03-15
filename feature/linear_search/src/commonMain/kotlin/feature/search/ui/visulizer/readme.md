These will define the state and UI for visualization the algo with controller
#### package:`contract` 
package will define the necessary interface and models,it does not
provide any implementations.

#### package:`controllers`
package will provide the implementation,there can be multiple implementations based on these contract
the UI will use the contracts ,it will not use the implementations so that later
we can change the implementation without effecting the UI
