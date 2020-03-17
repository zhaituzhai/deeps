package com.zhaojm.basal.javaio;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class DirList {

    public static void main(final String[] args) {
        File path = new File(".");
        String[] list;
        if(args.length == 0)
            list = path.list();
        else
            list = path.list((dir, name) -> name.endsWith(args[0]));
        for (String s : list) {
            System.out.println(s);
        }
    }

    private static void dirList1(final String[] args) {
        File path = new File(".");
        String[] list;
        if(args.length == 0)
            list = path.list();
        else
            list = path.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return Pattern.compile(args[0]).matcher(name).matches();
                }
            });
        for (String s : list) {
            System.out.println(s);
        }
    }

}
