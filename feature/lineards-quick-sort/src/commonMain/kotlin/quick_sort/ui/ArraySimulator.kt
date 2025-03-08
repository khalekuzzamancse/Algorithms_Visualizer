package quick_sort.ui

import core.commonui.array.VisualArrayFactory
import core.commonui.array.controller.VisualArrayController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface ArraySimulator {
    val arrayController:VisualArrayController
    fun movePointerI(index: Int?)
    fun movePointerJ(index: Int?)
    fun onPivotChange(index: Int)
    fun onSubArrayChange(low: Int, high: Int)
    fun swap(i: Int, j: Int)
    fun  clearPointer()
   companion object{
       fun create(list:List<Int>): ArraySimulator = ArraySimulatorImpl(list)
   }

}

private class ArraySimulatorImpl(
       list:List<Int>
) : ArraySimulator {
    companion object {
        private const val POINTER_I = "i"
        private const val POINTER_J = "j"
    //    private const val POINTER_PIVOT = "p" High is the pivot
        private const val POINTER_LOW = "low"
        private const val POINTER_HIGH = "High"
    }

    private val pointers = listOf(POINTER_I, POINTER_J, POINTER_LOW, POINTER_HIGH)
    override val arrayController = VisualArrayFactory.createController(
        itemLabels =list .map { it.toString() },
        pointersLabel = pointers
    )

    override fun movePointerI(index: Int?) {
        index?.let {
            arrayController.movePointer(POINTER_I, index)

        }

    }

    override fun movePointerJ(index: Int?) {
        index?.let {
            arrayController.movePointer(POINTER_J, index)
        }
    }

    override fun clearPointer() {
        arrayController.removePointers(pointers)
    }

    override fun onPivotChange(index: Int) {
     //   arrayController.movePointer(POINTER_PIVOT, index)
        //high is pivot
    }

    override fun onSubArrayChange(low: Int, high: Int) {
        arrayController.movePointer(POINTER_LOW, low)
        arrayController.movePointer(POINTER_HIGH, high)

        //InActive Part
//        for (i in 0..<low){
//            arrayController.changeElementColor(i, Color.Black)
//        }
//        for (i in high+1..list.lastIndex){
//            arrayController.changeElementColor(i, Color.Black)
//        }
//
//        for ( i in low..high){
//            arrayController.changeElementColor(i, Color.White)
//        }

    }

    override fun swap(i: Int, j: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            arrayController.swap(i=i,j=j, delay = 100)
        }

    }

}
