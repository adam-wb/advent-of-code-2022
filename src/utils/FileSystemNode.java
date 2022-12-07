package utils;

public class FileSystemNode {
    public String name;
    public int size;

    public FileSystemNode(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return String.format("%s (dir)", name);
        }
        return String.format("%s (file, size=%s)", name, size);
    }

}
