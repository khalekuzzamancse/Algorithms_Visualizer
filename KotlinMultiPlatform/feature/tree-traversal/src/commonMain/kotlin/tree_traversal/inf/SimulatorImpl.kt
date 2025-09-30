package tree_traversal.inf

import tree_traversal.domain.model.BFSCodeStateModel
import tree_traversal.domain.model.DFSCodeStateModel
import tree_traversal.domain.model.SimulationState
import tree_traversal.domain.model.TraversalType
import tree_traversal.domain.model.TreeNode
import tree_traversal.domain.service.PseudocodeGenerator
import tree_traversal.domain.service.Simulator

class SimulatorImpl internal  constructor(
    root: TreeNode,
    private val type: TraversalType
) : Simulator {

    private val iterator: Iterator<SimulationState>

    init {
        val sequence = Traversal(root =root, type =type).traverse()
        iterator = sequence.iterator()
    }

    /**
     * Moves to the next state in the simulation by delegating to the sequence iterator.
     *
     * @return The next [SimulationState] in the simulation.
     * @throws NoSuchElementException if the simulation has finished and there are no more states.
     */
    override fun next(): SimulationState {
        return if (iterator.hasNext()) {
            iterator.next()
        } else {
            when(type){
                TraversalType.BFS->   SimulationState.Finished(
                    PseudocodeGenerator.generate(type ,
                    BFSCodeStateModel()
                ))
                else-> SimulationState.Finished(
                    PseudocodeGenerator.generate(type ,
                    DFSCodeStateModel()
                ))
            }
        }
    }
}
