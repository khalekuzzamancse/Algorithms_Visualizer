package graphtopologicalsort.domain.model

data class EdgeModel(
    val id: String,
    val u: NodeModel,
    val v: NodeModel,
)