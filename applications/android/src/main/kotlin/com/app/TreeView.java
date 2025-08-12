package com.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class TreeView extends View {

    private Node<Integer> tree;

    public TreeView(Context context) {
        this(context, null);
    }

    public TreeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTree();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final float node_size = 10.f;
        final float scale = 10.0f;

        final float h = scale * node_size * tree.getDepth();

        final int y_offset = (int) ((getHeight() - h) / 2);
        final int x_offset = (int) node_size;

        canvas.translate(x_offset, y_offset);
        canvas.save();
        new DrawTree<>(tree, d -> (int) (scale * node_size * d), node_size)
                .draw(canvas, new Paint());
        canvas.restore();
    }

    private void initTree() {
        tree = new Node<>(0);
        tree.l = new Node<>(1);
        tree.r = new Node<>(2);
        tree.l.l = new Node<>(3);
        tree.l.l.l = new Node<>(7);
        tree.l.l.r = new Node<>(8);
        tree.l.r = new Node<>(4);
        tree.l.r.l = new Node<>(7);
        tree.l.r.r = new Node<>(8);
        tree.r.l = new Node<>(5);
        tree.r.r = new Node<>(6);
        tree.r.r.l = new Node<>(7);
        tree.r.r.r = new Node<>(8);
        tree.r.l.l = new Node<>(9);
        tree.r.l.r = new Node<>(10);
    }

    static class DrawTree<Data> {

        public final Node<Data> binTree;
        private final IntInt grid;
        private int i;
        private final float nodeSize;

        public DrawTree(Node<Data> tree, float nodeSize) {
            this(tree, y -> 100 * y, nodeSize);
        }

        public DrawTree(Node<Data> tree, IntInt grid, float nodeSize) {
            this.binTree = tree;
            this.grid = grid;
            this.nodeSize = nodeSize;
        }

        public void draw(Canvas c, Paint p) {
            i = 0;
            drawKnuth(c, p, binTree, 0);
        }

        private Point drawKnuth(Canvas c, Paint p, Node<Data> binTree, int depth) {
            Point l_root = null;
            Point r_root = null;

            if (binTree.l != null) {
                l_root = drawKnuth(c, p, binTree.l, depth + 1);
            }

            int nx = grid.map(i);
            int ny = grid.map(depth);
            c.drawCircle(nx, ny, nodeSize, p);
            i++;

            if (binTree.r != null) {
                r_root = drawKnuth(c, p, binTree.r, depth + 1);
            }


            if (binTree.l != null) {
                c.drawLine(nx, ny, l_root.x, l_root.y, p);
            }

            if (binTree.r != null) {
                c.drawLine(nx, ny, r_root.x, r_root.y, p);
            }

            return new Point(nx, ny);
        }

    }

    static class Node<Data> {
        Node<Data> l;
        Node<Data> r;
        public final Data data;

        public Node(Data d) {
            data = d;
        }

        /**
         * Flattens a tree in to an list using inorder traversal.
         *
         * @return
         */
        public List<Node<Data>> flatten() {
            final int depth = getDepth();
            ArrayList<Node<Data>> result = new ArrayList<>(2 * depth + 1);

            if (l != null)
                result.addAll(l.flatten());
            result.add(this);
            if (r != null)
                result.addAll(r.flatten());

            result.trimToSize();
            return result;
        }

        public int getDepth() {
            // Leaf
            if (l == null && r == null) return 0;
            if (l == null) return 1 + r.getDepth();
            if (r == null) return 1 + l.getDepth();
            return 1 + Math.max(l.getDepth(), r.getDepth());
        }

        @NonNull
        @Override
        public String toString() {
            return String.valueOf(data);
        }
    }

    @FunctionalInterface
    private interface IntInt {
        int map(int in);
    }
}
