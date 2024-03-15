
#Understainding algorithm
in order to visualize it we have to understand how the algorithm work.

* from [0 ..i-1] or [0 ...j] are already sorted.
* now we want to put the key in this sorted part.
* so we have to find the appropriate position for that.
* let start from right to left means start from j.
* if value at[j]>key that means the if we put key at[j] then this will become unsorted
* that conclude that found is a value(key) that is less that value at[j],that means
* that value at[j] should move one step right from it current position.
* so   list[j+1]=list[j].
* but where the old value of list[j+1],means now the
* list[j] and list[j+1] has the same value,if this the first step means j=i-1 then list[j+1]=list[i]
* means we lost the value of list[i],but since this first step so we already stored the value of
* list[i] at variable=key
* though at this moment list[j] and list[j+1] has the same value,the old value of list[j+1] is at key(If
* this the first iteration of inner while loop.
* next consider the 2nd iteration:::
* now list[j] has duplicate element because the list[j] has take right shift by 1.
* now assume that current we decrease the j by one means j--.
* at this point if list[j]>key that means this value also need to shift by 1 because we can not
* put the key at this position,since list[j]>key that means key never can sit at [j] in order to the array be
* sorted.
* if so list[j+1]=list[j] ,but in that case we are overriding and loosing the value of list[j+1]
* but this not a problem because the list[j+1] is a duplicate copy,so it is okay to override it.
* so the same goes until j=0(if value at[j]>key]
* but what if we found a position j such as value at[j]<=key ,since this part is sorted so now
* we can put the key at next to j.
* so list[j+1]=key, since we already prove that [j+1] either has the duplicate element or it has
* has same value as key(if j+1=i),so putting key at[j+1] will does loose any value of the array.
* The conclusion is, [j+1] will either has the duplicate element or it is the key([i]

### Implementation for visualization
the challenging part of it:
We have to swap only once,
we must have to store the key as separate variable otherwise we are not able to recover the A[i] after shiting
let implement it first without storing the A[i],then find the solution of it.

###
one of that way that can be, 
find the position of key and then shift the remaking element one by one 
in that case we are not making the whole array at once,instead we are inserting element  one by one 


These will define the state and UI for visualization the algo with controller
#### package:`contract` 
package will define the necessary interface and models,it does not
provide any implementations.

#### package:`controllers`
package will provide the implementation,there can be multiple implementations based on these contract
the UI will use the contracts ,it will not use the implementations so that later
we can change the implementation without effecting the UI
