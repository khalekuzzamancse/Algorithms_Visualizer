@file:Suppress("unused","localVariableName")

package core.ui.graph.editor.factory

import androidx.compose.ui.geometry.Offset
import core.ui.graph.common.model.*


internal object SavedGraphProvider {

    fun getSampleGraph(): Pair<List<EditorNodeModel>, List<EditorEdgeModel>> {
        val nodes = listOf(
            EditorNodeModel(
                id = "c66097e1-0d1c-4c3d-b550-75c9787d054c",
                label = "1",
                topLeft = Offset(384.0f, 48.0f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "4eb11290-f8e5-4329-9b0f-94ee6fe6590c",
                label = "2",
                topLeft = Offset(312.0f, 121.0f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "3aeb3a6b-e8fc-4182-af07-c937f54c88aa",
                label = "3",
                topLeft = Offset(448.0f, 118.0f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "41bb5ef6-d258-4f90-b618-d7a48ce5b4be",
                label = "4",
                topLeft = Offset(243.0f, 192.9f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "1942ed99-cfa8-4e49-ac5b-d808b110e263",
                label = "5",
                topLeft = Offset(365.8f, 198.1f),
                exactSizePx = 48.0f,
            )
        )
        val edges = listOf(
            EditorEdgeModel(
                id = "c51a1854-2782-41c8-b81b-4b18d0b2020d",
                start = Offset(400.0f, 74.0f),
                end = Offset(345.0f, 130.9f),
                control = Offset(372.5f, 102.4f),
                cost = null,
                directed = true,
                minTouchTargetPx = 30.0f
            ),
            EditorEdgeModel(
                id = "669a6c7a-1362-446c-a5da-4e0c2be80463",
                start = Offset(323.0f, 150.0f),
                end = Offset(276.1f, 203.0f),
                control = Offset(299.6f, 176.5f),
                cost = null,
                directed = true,
                minTouchTargetPx = 30.0f
            ),
            EditorEdgeModel(
                id = "e14664da-507a-429c-a8c9-01357b08ddeb",
                start = Offset(332.0f, 147.0f),
                end = Offset(380.9f, 216.9f),
                control = Offset(356.5f, 182.0f),
                cost = null,
                directed = true,
                minTouchTargetPx = 30.0f
            ),
            EditorEdgeModel(
                id = "5549a108-336c-4185-af07-8d52d9f422c6",
                start = Offset(413.0f, 68.0f),
                end = Offset(467.0f, 131.9f),
                control = Offset(440.0f, 99.9f),
                cost = null,
                directed = true,
                minTouchTargetPx = 30.0f
            )
        )
        return Pair(nodes, edges)
    }


    fun getTopologicalSortDemoGraph(): Pair<List<EditorNodeModel>, List<EditorEdgeModel>> {
        val nodes = listOf(
            EditorNodeModel(
                id = "684e5b6f-c2ed-4427-b7e2-7851fac903d6",
                label = "C",
                topLeft = Offset(120.9f, 11.0f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "00046efa-b70a-46ee-a427-ffed7f17632a",
                label = "Java",
                topLeft = Offset(121.3f, 108.0f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "276fc056-4b8a-42a9-b92b-438fbf535ea5",
                label = "DS",
                topLeft = Offset(122.1f, 209.0f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "bb901cf5-bed0-436f-8a99-d9e005c90a41",
                label = "SE",
                topLeft = Offset(20.8f, 286.9f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "719078b9-4c8c-4288-a28b-27e05a856f6d",
                label = "OS",
                topLeft = Offset(117.2f, 288.9f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "40d6b42a-eac6-4885-98e6-0ee0b1da80b6",
                label = "Algo",
                topLeft = Offset(200.1f, 290.1f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "0ec44d60-3ffb-4967-9016-5e7f7208168a",
                label = "DM",
                topLeft = Offset(17.0f, 153.0f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "6931ab72-1ed8-42e1-82a1-1e01c39f4f14",
                label = "AI",
                topLeft = Offset(114.2f, 383.9f),
                exactSizePx = 48.0f,
            )
        )

        val edges = listOf(
            EditorEdgeModel(
                id = "7dd81457-5d94-4039-a354-5046f3627ab7",
                start = Offset(141.0f, 57.0f),
                end = Offset(143.0f, 110.9f),
                control = Offset(142.0f, 81.9f),
                cost = null,
                directed = true,
            ),
            EditorEdgeModel(
                id = "e8cb307d-a7e3-4bfd-82e4-28e6e7ca22ca",
                start = Offset(143.0f, 154.0f),
                end = Offset(146.0f, 209.9f),
                control = Offset(144.5f, 181.9f),
                cost = null,
                directed = true,
            ),
            EditorEdgeModel(
                id = "835aa445-f815-4707-9517-1e8c2fb8c22a",
                start = Offset(56.0f, 189.0f),
                end = Offset(123.9f, 230.9f),
                control = Offset(89.9f, 209.9f),
                cost = null,
                directed = true,
            ),
            EditorEdgeModel(
                id = "297f5054-2461-48b3-aa6b-19e4c3c9bfa7",
                start = Offset(139.0f, 244.0f),
                end = Offset(138.9f, 290.0f),
                control = Offset(138.9f, 267.0f),
                cost = null,
                directed = true,
            ),
            EditorEdgeModel(
                id = "a4f22b42-1e67-4516-8513-51a4575f8062",
                start = Offset(135.0f, 246.0f),
                end = Offset(61.9f, 295.0f),
                control = Offset(98.4f, 270.5f),
                cost = null,
                directed = true,
            ),
            EditorEdgeModel(
                id = "b2f9778d-a037-4dcf-879f-0e2ecccdc6c7",
                start = Offset(150.0f, 245.0f),
                end = Offset(210.9f, 296.0f),
                control = Offset(180.4f, 270.5f),
                cost = null,
                directed = true,
            ),
            EditorEdgeModel(
                id = "56e69a96-46c6-4292-85d9-735030ab9d05",
                start = Offset(212.0f, 330.0f),
                end = Offset(154.1f, 392.8f),
                control = Offset(183.0f, 361.4f),
                cost = null,
                directed = true,
            ),
            EditorEdgeModel(
                id = "3dfe3970-1eaf-441b-b32e-8b7711bd9fe0",
                start = Offset(137.0f, 329.0f),
                end = Offset(135.2f, 385.8f),
                control = Offset(136.1f, 357.4f),
                cost = null,
                directed = true,
            )
        )
        return Pair(nodes, edges)
    }

    fun getDijkstraGraph(): Pair<List<EditorNodeModel>, List<EditorEdgeModel>> {
        val nodes = listOf(
            EditorNodeModel(
                id = "A",
                label = "A",
                topLeft = Offset(47.0f, 115.9f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "B",
                label = "B",
                topLeft = Offset(190.0f, 56.0f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "C",
                label = "C",
                topLeft = Offset(360.0f, 49.9f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "D",
                label = "D",
                topLeft = Offset(367.6f, 172.1f),
                exactSizePx = 48.0f,
            ),
            EditorNodeModel(
                id = "E",
                label = "E",
                topLeft = Offset(196.9f, 173.0f),
                exactSizePx = 48.0f,
            )
        )

        val minTouch = 30f
        val edges = listOf(
            // (A, B)
            EditorEdgeModel(
                id = "edge1",
                start = Offset(87.0f, 131.0f),
                end = Offset(190.9f, 89.0f),
                control = Offset(140.9f, 110.0f),
                cost = "10",
                minTouchTargetPx = minTouch
            ),

            // (A, E)
            EditorEdgeModel(
                id = "edge2",
                start = Offset(81.0f, 155.0f),
                end = Offset(198.9f, 190.9f),
                control = Offset(141.0f, 173.0f),
                cost = "5",
                directed = false,
                minTouchTargetPx = 30.0f
            ),

            // (B, E)
            EditorEdgeModel(
                id = "edge3",
                start = Offset(208.0f, 176.0f),
                end = Offset(206.0f, 90.1f),
                control = Offset(187.1f, 139.1f),
                cost = "3",
                directed = false,
                minTouchTargetPx = 30.0f
            ),

            // (E, B)
            EditorEdgeModel(
                id = "edge4",
                start = Offset(222.0f, 94.0f),
                end = Offset(229.0f, 185.9f),
                control = Offset(255.5f, 131.6f),
                cost = "2",
                directed = false,
                minTouchTargetPx = 30.0f
            ),

            // (C, D)
            EditorEdgeModel(
                id = "edge5",
                start = Offset(370.0f, 90.0f),
                end = Offset(380.0f, 177.9f),
                control = Offset(344.6f, 137.9f),
                cost = "4",
                directed = false,
                minTouchTargetPx = 30.0f
            ),

            // (D, C)
            EditorEdgeModel(
                id = "edge6",
                start = Offset(397.0f, 177.0f),
                end = Offset(396.0f, 92.1f),
                control = Offset(430.4f, 133.1f),
                cost = "6",
                directed = false,
                minTouchTargetPx = 30.0f
            ),

            // (E, C)
            EditorEdgeModel(
                id = "edge7",
                start = Offset(234.0f, 186.0f),
                end = Offset(360.9f, 83.0f),
                control = Offset(300.4f, 134.5f),
                cost = "9",
                directed = false,
                minTouchTargetPx = 30.0f
            ),

            // (B, E)
            EditorEdgeModel(
                id = "edge8",
                start = Offset(239.0f, 195.0f),
                end = Offset(370.9f, 190.0f),
                control = Offset(302.4f, 195.0f),
                cost = "2",
                directed = false,
                minTouchTargetPx = 30.0f
            ),

            // (D, A)
            EditorEdgeModel(
                id = "edge9",
                start = Offset(375.0f, 183.0f),
                end = Offset(95.1f, 141.0f),
                control = Offset(228.1f, 163.5f),
                cost = "7",
                directed = false,
                minTouchTargetPx = 30.0f
            ),

            // (B, C)
            EditorEdgeModel(
                id = "edge10",
                start = Offset(233.9f, 78f),
                end = Offset(360.6f, 77.2f),
                control = Offset(298.0f, 77.7f),
                cost = "1",
                directed = false,
                minTouchTargetPx = 30.0f
            )
        )
        return Pair(nodes, edges)
    }

    fun getMSTGraph(): Pair<List<EditorNodeModel>, List<EditorEdgeModel>> {

        val nodes = listOf(
            EditorNodeModel(
                id = "A",
                label = "A",
                topLeft = Offset(308.0f, 17.125f),
                exactSizePx = 48.0f
            ),
            EditorNodeModel(
                id = "C",
                label = "C",
                topLeft = Offset(277.875f, 165.125f),
                exactSizePx = 48.0f
            ),
            EditorNodeModel(
                id = "D",
                label = "D",
                topLeft = Offset(499.875f, 165.75f),
                exactSizePx = 48.0f
            ),
            EditorNodeModel(
                id = "B",
                label = "B",
                topLeft = Offset(93.75f, 165.125f),
                exactSizePx = 48.0f
            ),
            EditorNodeModel(
                id = "F",
                label = "F",
                topLeft = Offset(408.91162f, 311.91162f),
                exactSizePx = 48.0f
            ),
            EditorNodeModel(
                id = "E",
                label = "E",
                topLeft = Offset(172.125f, 309.0f),
                exactSizePx = 48.0f
            )
        )

        val edges = listOf(
            EditorEdgeModel(
                id = "1",
                start = Offset(345.0f, 52.0f),
                end = Offset(506.875f, 178.0f),
                control = Offset(425.9375f, 115.0f),
                cost = "4",
                directed = true
            ),
            EditorEdgeModel(
                id = "2",
                start = Offset(518.0f, 205.0f),
                end = Offset(440.875f, 319.0f),
                control = Offset(479.4375f, 262.0f),
                cost = "2",
                directed = true
            ),
            EditorEdgeModel(
                id = "3",
                start = Offset(314.0f, 51.0f),
                end = Offset(132.125f, 174.0f),
                control = Offset(223.0625f, 112.5f),

                cost = "6" //may seems like 9,
                ,directed = true
            ),
            EditorEdgeModel(
                id = "4",
                start = Offset(328.0f, 57.0f),
                end = Offset(302.0f, 172.875f),
                control = Offset(315.0f, 114.9375f),
                cost = "9"//may seems like 6
                , directed = true
            ),
            EditorEdgeModel(
                id = "5",
                start = Offset(131.0f, 191.0f),
                end = Offset(282.875f, 192.0f),
                control = Offset(206.9375f, 191.5f),
                cost = "3",
                directed = true
            ),
            EditorEdgeModel(
                id = "6",
                start = Offset(289.0f, 205.0f),
                end = Offset(203.0f, 329.875f),
                control = Offset(246.0f, 267.4375f),
                cost = "7",
                directed = true
            ),
            EditorEdgeModel(
                id = "7",
                start = Offset(120.0f, 202.0f),
                end = Offset(182.91162f, 324.91162f),
                control = Offset(151.45581f, 263.4558f),
                cost = "10",
                directed = true
            ),
            EditorEdgeModel(
                id = "8",
                start = Offset(296.0f, 185.0f),
                end = Offset(423.875f, 336.0f),
                control = Offset(359.9375f, 260.5f),
                cost = "5",
                directed = true
            ),
            EditorEdgeModel(
                id = "7",
                start = Offset(504.0f, 189.0f),
                end = Offset(314.125f, 193.0f),
                control = Offset(409.0625f, 191.0f),
                cost = "8"//
                , directed = true
            )
        )
        return Pair(nodes, edges)
    }

    fun getTree(): Pair<List<EditorNodeModel>, List<EditorEdgeModel>> {
        val nodeSizePx=48.0f
        val nodes = listOf(
            EditorNodeModel(
                id = "1",
                label = "1",
                topLeft = Offset(384.0f, 48.0f),
                exactSizePx = nodeSizePx
            ),
            EditorNodeModel(
                id = "2",
                label = "2",
                topLeft = Offset(312.0f, 121.0f),
                exactSizePx =nodeSizePx
            ),
            EditorNodeModel(
                id = "3",
                label = "3",
                topLeft = Offset(448.0f, 118.0f),
                exactSizePx = nodeSizePx
            ),
            EditorNodeModel(
                id = "4",
                label = "4",
                topLeft = Offset(243.0f, 192.9f),
                exactSizePx =nodeSizePx
            ),
            EditorNodeModel(
                id = "5",
                label = "5",
                topLeft = Offset(365.8f, 198.1f),
                exactSizePx = nodeSizePx
            )
        )
        val edges = listOf(
            EditorEdgeModel(
                id = "1",
                start = Offset(400.0f, 74.0f),
                end = Offset(345.0f, 130.9f),
                control = Offset(372.5f, 102.4f),
                cost = null
            ),
            EditorEdgeModel(
                id = "2",
                start = Offset(323.0f, 150.0f),
                end = Offset(276.1f, 203.0f),
                control = Offset(299.6f, 176.5f),
                cost = null
            ),
            EditorEdgeModel(
                id = "3",
                start = Offset(332.0f, 147.0f),
                end = Offset(380.9f, 216.9f),
                control = Offset(356.5f, 182.0f),
                cost = null
            ),
            EditorEdgeModel(
                id = "4",
                start = Offset(413.0f, 68.0f),
                end = Offset(467.0f, 131.9f),
                control = Offset(440.0f, 99.9f),
                cost = null
            )
        )
        return Pair(nodes, edges)
    }


}


class GraphProvider(private val density: Float) {
    val nodes = listOf(
        EditorNodeModel(id = "A", label = "A", topLeft = Offset(47.0f, 115.9f), exactSizePx = 48.0f),
        EditorNodeModel(id = "B", label = "B", topLeft = Offset(190.0f, 56.0f), exactSizePx = 48.0f),
        EditorNodeModel(id = "C", label = "C", topLeft = Offset(360.0f, 49.9f), exactSizePx = 48.0f),
        EditorNodeModel(id = "D", label = "D", topLeft = Offset(367.6f, 172.1f), exactSizePx = 48.0f),
        EditorNodeModel(
            id = "E",
            label = "E",
            topLeft = Offset(196.9f, 173.0f),
            exactSizePx = 48.0f,
        ))
    val edges = listOf(
        // (A, B)
        EditorEdgeModel(
            id = "edge1",
            start = Offset(87.0f, 131.0f),
            end = Offset(190.9f, 89.0f),
            control = Offset(140.9f, 110.0f),
            cost = "10",
            directed=true
        ),

        // (A, E)
        EditorEdgeModel(
            id = "edge2",
            start = Offset(81.0f, 155.0f),
            end = Offset(198.9f, 190.9f),
            control = Offset(141.0f, 173.0f),
            cost = "5",
            directed = true,
        ),

        // (B, E)
        EditorEdgeModel(
            id = "edge3",
            start = Offset(208.0f, 176.0f),
            end = Offset(206.0f, 90.1f),
            control = Offset(187.1f, 139.1f),
            cost = "3",
            directed = true,
        ),

        // (E, B)
        EditorEdgeModel(
            id = "edge4",
            start = Offset(222.0f, 94.0f),
            end = Offset(229.0f, 185.9f),
            control = Offset(255.5f, 131.6f),
            cost = "2",
            directed = true,
        ),

        // (C, D)
        EditorEdgeModel(
            id = "edge5",
            start = Offset(370.0f, 90.0f),
            end = Offset(380.0f, 177.9f),
            control = Offset(344.6f, 137.9f),
            cost = "4",
            directed = true,
        ),

        // (D, C)
        EditorEdgeModel(
            id = "edge6",
            start = Offset(397.0f, 177.0f),
            end = Offset(396.0f, 92.1f),
            control = Offset(430.4f, 133.1f),
            cost = "6",
            directed = true,
        ),

        // (E, C)
        EditorEdgeModel(
            id = "edge7",
            start = Offset(234.0f, 186.0f),
            end = Offset(360.9f, 83.0f),
            control = Offset(300.4f, 134.5f),
            cost = "9",
            directed = true,
        ),

        // (B, E)
        EditorEdgeModel(
            id = "edge8",
            start = Offset(239.0f, 195.0f),
            end = Offset(370.9f, 190.0f),
            control = Offset(302.4f, 195.0f),
            cost = "2",
            directed = true,
        ),

        // (D, A)
        EditorEdgeModel(
            id = "edge9",
            start = Offset(375.0f, 183.0f),
            end = Offset(95.1f, 141.0f),
            control = Offset(228.1f, 163.5f),
            cost = "7",
            directed = true,
        ),

        // (B, C)
        EditorEdgeModel(
            id = "edge10",
            start = Offset(233.9f, 78f),
            end = Offset(360.6f, 77.2f),
            control = Offset(298.0f, 77.7f),
            cost = "1",
            directed = true,
        )
    )

    fun getDemoGraph(): Pair<List<EditorNodeModel>, List<EditorEdgeModel>> {
//        println("DeviceDensity:$density")
        //TODO:Convert value to DP first so that it works for any devices
        //Then convert dp to px ,so final=density*currentOffset where current Offset will be treated as DP though it is in px
        //But will works because we are taking the  density



        val edgeList=edges.map { edge ->
            edge.copy(start = edge.start * density, end = edge.end * density,
                control = edge.control * density, minTouchTargetPx = edge.minTouchTargetPx * density)
        }
        return Pair(nodes.map { node -> node.copy(topLeft = node.topLeft * density, exactSizePx = node.exactSizePx * density)}, edgeList)

    }

}