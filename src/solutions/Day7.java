package solutions;

import utils.AdventOfCodePuzzle;
import utils.FileSystemNode;
import utils.TreeNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day7 extends AdventOfCodePuzzle {

    private static final int DIR_SIZE_THRESHOLD = 100000;
    private static final int TOTAL_SPACE = 70000000;
    private static final int NEEDED_SPACE = 30000000;

    public Day7() throws IOException {
        super(7);
    }

    @Override
    public void part1() {
        TreeNode<FileSystemNode> fileStructure = parseInput();

        List<Integer> dirSizes = getDirSizes(fileStructure);
        List<Integer> thresholdDirSizes = dirSizes.stream().filter(size -> size < DIR_SIZE_THRESHOLD).toList();
        int dirSizesSum = thresholdDirSizes.stream().reduce(Integer::sum).orElse(0);

        System.out.printf("Part 1 solution - %s\n", dirSizesSum);
    }

    @Override
    public void part2() {
        TreeNode<FileSystemNode> fileStructure = parseInput();

        int spaceToSave = NEEDED_SPACE - (TOTAL_SPACE - sumNodeSize(fileStructure));
        int smallestDirToDeleteSize = getDirSizes(fileStructure).stream()
                .filter(size -> size >= spaceToSave)
                .sorted()
                .findFirst()
                .orElse(0);
        System.out.printf("Part 2 solution - %s\n", smallestDirToDeleteSize);
    }

    private List<Integer> getDirSizes(TreeNode<FileSystemNode> node) {
        List<Integer> dirSizes = new ArrayList<>();
        if (node.getData().size != 0) {
            System.out.printf("%s is not a directory, skipping\n", node.getData().name);
            return dirSizes;
        }
        System.out.printf("Calculating total size of files inside %s\n", node.getData().name);
        dirSizes.add(sumNodeSize(node));
        node.getChildren().forEach(child -> dirSizes.addAll(getDirSizes(child)));
        return dirSizes;
    }

    private int sumNodeSize(TreeNode<FileSystemNode> node) {
        if (node.isLeaf()) {
            return node.getData().size;
        }
        return node.getChildren().stream().map(this::sumNodeSize).reduce(Integer::sum).orElse(0);
    }

    private TreeNode<FileSystemNode> parseInput() {
        List<String> commands = List.of(puzzleInput.split("\\R"));

        TreeNode<FileSystemNode> root = new TreeNode<>(new FileSystemNode("/", 0));
        TreeNode<FileSystemNode> currentDir = root;

        for (String command : commands) {
            if (command.equals("$ ls") || command.equals("$ cd /")) {
                continue;
            }
            if (command.startsWith("$ cd")) {
                String dirName = getName(command);
                if (Objects.equals(dirName, "..")) {
                    currentDir = currentDir.getParent();
                    System.out.printf("Moved up one level to %s\n", currentDir.getData().name);
                } else {
                    currentDir = getChildDir(currentDir, dirName);
                    System.out.printf("Changed directory to %s\n", currentDir.getData().name);
                }
                continue;
            }
            if (command.startsWith("dir")) {
                FileSystemNode childDir = new FileSystemNode(getName(command), 0);
                currentDir.addChild(childDir);
                System.out.printf("Added new child directory %s\n", childDir.name);
                continue;
            }
            // Otherwise must be a file
            FileSystemNode file = new FileSystemNode(getName(command), getSize(command));
            currentDir.addChild(file);
            System.out.printf("Added new file %s\n", file.name);
        }

        return root;
    }

    private String getName(String command) {
        String[] commandComponents = command.split(" ");
        return commandComponents[commandComponents.length - 1];
    }

    private int getSize(String command) {
        return Integer.parseInt(command.split(" ")[0]);
    }

    private TreeNode<FileSystemNode> getChildDir(TreeNode<FileSystemNode> currentDir, String dirName) {
        List<TreeNode<FileSystemNode>> childDirectories = currentDir.getChildren();
        return childDirectories.stream().filter(child -> child.getData().name.equals(dirName)).findFirst().orElseThrow();
    }
}
