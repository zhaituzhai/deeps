package com.zhaojm.basal.javaio;

import java.io.File;
import java.util.*;

public class Directory {

    public static File[] local(File file, String regex){
        return file.listFiles((dir, name) -> new File(name).getName().endsWith(regex));
    }

    public static File[] local(String path, String regex){
        return local(new File(path), regex);
    }

    public static class TreeInfo implements Iterable<File>{
        public List<File> files = new ArrayList<>();
        public List<File> dirs = new ArrayList<>();
        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        void addAll(TreeInfo other){
            files.addAll(other.files);
            dirs.addAll(other.dirs);
        }

        @Override
        public String toString() {
            return "dirs: " + pformat(dirs) + "\n\nfiles: " + pformat(files);
        }
    }

    public static TreeInfo walk(String start, String regex){
        return recurseDirs(new File(start), regex);
    }

    public static TreeInfo walk(String start){
        return recurseDirs(new File(start), ".*");
    }

    public static TreeInfo walk(File start, String regex){
        return recurseDirs(start, regex);
    }

    public static TreeInfo walk(File start){
        return recurseDirs(start, ".*");
    }

    private static TreeInfo recurseDirs(File startDir, String regex) {
        TreeInfo result = new TreeInfo();
        for (File item : startDir.listFiles()) {
            if(item.isDirectory()){
                result.dirs.add(item);
                result.addAll(recurseDirs(item, regex));
            }else
                if(item.getName().endsWith(regex))
                    result.files.add(item);
        }
        return result;
    }

    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println(walk("."));
        }else {
            for ( String arg : args){
                System.out.println(walk(".", arg));
            }
        }
    }

    public static String pformat(Collection<?> c) {
        if(c.size() == 0) return "[]";
        StringBuilder result = new StringBuilder("[");
        for(Object elem : c) {
            if(c.size() != 1)
                result.append("\n ");
            result.append(elem);
        }
        if(c.size() != 1)
            result.append("\n");
        result.append("]");
        return result.toString();
    }

}
