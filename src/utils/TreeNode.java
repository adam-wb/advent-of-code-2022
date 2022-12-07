package utils;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {
    private final T data;
    private final List<TreeNode<T>> children;
    private TreeNode<T> parent;

    public TreeNode(T initialData) {
        this.data = initialData;
        this.children = new ArrayList<>();
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    public T getData() {
        return data;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s\n", data.toString()));
        children.forEach(child -> sb.append(child.toString()));
        return sb.toString();
    }
}
