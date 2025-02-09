package tree.infrastructure.factory

import tree.domain.model.BFSCodeStateModel
import tree.domain.model.DFSCodeStateModel
import tree.domain.model.SimulationState
import tree.domain.model.TraversalType
import tree.domain.model.TreeNode
import tree.domain.service.PseudocodeGenerator
import tree.domain.service.Simulator

class SimulatorImpl internal  constructor(
    root:TreeNode,
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
                TraversalType.BFS->   SimulationState.Finished(PseudocodeGenerator.generate(type ,
                    BFSCodeStateModel()
                ))
                else->SimulationState.Finished(PseudocodeGenerator.generate(type ,
                    DFSCodeStateModel()
                ))
            }
        }
    }
}
