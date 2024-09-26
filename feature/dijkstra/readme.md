Resources
https://youtu.be/QLUCdAURRzI?list=PL6KMWPQP_DM8t5pQmuLlarpmVc47DVXWd


GraphEditorControllerImplLog::: :-> [(A,B), (A,E), (D,C), (C,D), (B,E), (E,B), (E,C), (D,A)]
[GraphViewerNodeModel(id=cf85eb11-4783-43b5-b028-ba7267b10ac4, label=B, topLeft=Offset(190.0, 56.0), exactSizePx=48.0), GraphViewerNodeModel(id=102fc0de-1935-4053-8203-f3f2f3e886e2, label=A, topLeft=Offset(47.0, 115.9), exactSizePx=48.0), GraphViewerNodeModel(id=8d690c9c-50fb-434f-9148-9c637e8ede87, label=C, topLeft=Offset(360.0, 49.9), exactSizePx=48.0), GraphViewerNodeModel(id=305dd911-643b-4b1d-aef1-ad29994a5a11, label=D, topLeft=Offset(367.6, 172.1), exactSizePx=48.0), GraphViewerNodeModel(id=b9dd8862-5e8b-4250-834c-eb273e4f39f1, label=E, topLeft=Offset(196.9, 173.0), exactSizePx=48.0)]
[GraphViewerEdgeModel(id=48eaf4b9-e170-4e8b-a260-783b141392ed, start=Offset(87.0, 131.0), end=Offset(194.9, 89.0), control=Offset(140.9, 110.0), cost=null, isDirected=false), GraphViewerEdgeModel(id=e1ffb8b4-ef13-4eb4-94c4-7535b364574e, start=Offset(236.0, 78.0), end=Offset(359.9, 76.0), control=Offset(297.9, 77.0), cost=null, isDirected=false), GraphViewerEdgeModel(id=865a7168-07fe-4f25-98e2-c7d8c00ee305, start=Offset(81.0, 155.0), end=Offset(200.9, 190.9), control=Offset(141.0, 173.0), cost=null, isDirected=false), GraphViewerEdgeModel(id=d0320106-7ef0-466d-858e-3c77724a755c, start=Offset(239.0, 195.0), end=Offset(365.9, 195.0), control=Offset(302.4, 195.0), cost=null, isDirected=false), GraphViewerEdgeModel(id=948edcbb-b983-464f-b04d-5c83d58ec881, start=Offset(397.0, 177.0), end=Offset(396.0, 91.1), control=Offset(430.4, 133.1), cost=null, isDirected=false), GraphViewerEdgeModel(id=d5977761-e3fd-440e-bbef-12e81e1b5b76, start=Offset(370.0, 90.0), end=Offset(385.0, 177.9), control=Offset(344.6, 137.9), cost=null, isDirected=false), GraphViewerEdgeModel(id=b7bcbabd-c4a5-40ec-b5ca-ef99fd2f3d7c, start=Offset(222.0, 94.0), end=Offset(229.0, 180.9), control=Offset(255.5, 131.6), cost=null, isDirected=false), GraphViewerEdgeModel(id=d57f4640-be69-4278-b2ad-19f59893fe30, start=Offset(208.0, 176.0), end=Offset(206.0, 100.1), control=Offset(187.1, 139.1), cost=null, isDirected=false), GraphViewerEdgeModel(id=5c7515a9-8375-4d8f-b1d8-f3dfd8729ad5, start=Offset(234.0, 186.0), end=Offset(366.9, 83.0), control=Offset(300.4, 134.5), cost=null, isDirected=false), GraphViewerEdgeModel(id=cc061559-c05e-4292-82e5-080170f78e8a, start=Offset(369.0, 186.0), end=Offset(87.1, 141.0), control=Offset(228.1, 163.5), cost=null, isDirected=false)]
- What are the possible state that will change?
* - Edge cost/weight  is fixed
* - All nodes are fixed
* - Distance will be updated
- On start initial the distance will with ∞ off all node, the 0 for the source that means
 we have to change once or more nodes distance so one of the state is to pass, instead of 
pass the nodes we can pass their id only because the remaining are the same
the set of nodes ,but if we pass the id then we have to pass the updated distance also
that is why it is better to pass the nodes itself
- Via which node updating the distance means need to know the processing node
- Via which edge updating the distance means need to know the processing edge
- Force to edge weight be positive because -ve weight will not work here
- For any node the edge will minimum cost will be used to relax or update so you can 
get the minimum cost end
- Graph must be directed and weighted with positive values