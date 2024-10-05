package graphbfs.domain.model

enum class ColorModel {
    /**Not discovered yet*/
    White,
    /**Discovered but not process is not finished*/
    Gray,
    /**Processed means Discovered and  process is  finished*/
    Black,
}