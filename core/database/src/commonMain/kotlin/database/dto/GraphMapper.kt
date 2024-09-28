package database.dto

import database.schema.CoordinateSchema
import database.schema.EdgeSchema
import database.schema.NodeSchema

internal object GraphMapper {

    // Coordinate Mapper
    fun CoordinateEntity.toSchema(): CoordinateSchema {
        return CoordinateSchema(
            x = this.x,
            y = this.y
        )
    }

    fun CoordinateSchema.toEntity(): CoordinateEntity {
        return CoordinateEntity(
            x = this.x,
            y = this.y
        )
    }

    // Node Mapper
    fun NodeEntity.toSchema(): NodeSchema {
        return NodeSchema(
            id = this.id,
            distance = this.distance,
            label = this.label,
            topLeft = this.topLeft.toSchema(),
            exactSizePx = this.exactSizePx
        )
    }

    fun NodeSchema.toEntity(): NodeEntity {
        return NodeEntity(
            id = this.id,
            distance = this.distance,
            label = this.label,
            topLeft = this.topLeft.toEntity(),
            exactSizePx = this.exactSizePx
        )
    }

    // Edge Mapper
    fun EdgeEntity.toSchema(): EdgeSchema {
        return EdgeSchema(
            id = this.id,
            start = this.start.toSchema(),
            end = this.end.toSchema(),
            control = this.control.toSchema(),
            cost = this.cost,
            directed = this.directed
        )
    }

    fun EdgeSchema.toEntity(): EdgeEntity {
        return EdgeEntity(
            id = this.id,
            start = this.start.toEntity(),
            end = this.end.toEntity(),
            control = this.control.toEntity(),
            cost = this.cost,
            directed = this.directed
        )
    }

    // Convert List<NodeEntity> to List<NodeSchema>
    fun List<NodeEntity>.toNodeSchemaList(): List<NodeSchema> {
        return this.map { it.toSchema() }
    }

    // Convert List<NodeSchema> to List<NodeEntity>
    fun List<NodeSchema>.toNodeEntityList(): List<NodeEntity> {
        return this.map { it.toEntity() }
    }

    // Convert List<EdgeEntity> to List<EdgeSchema>
    fun List<EdgeEntity>.toEdgeSchemaList(): List<EdgeSchema> {
        return this.map { it.toSchema() }
    }

    // Convert List<EdgeSchema> to List<EdgeEntity>
    fun List<EdgeSchema>.toEdgeEntityList(): List<EdgeEntity> {
        return this.map { it.toEntity() }
    }
}
