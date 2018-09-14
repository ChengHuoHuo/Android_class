package com.example.a44223.helloworld;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * Created by 44223 on 2018/4/25.
 */

class MyFilter implements FilenameFilter{
    private String type;
    public MyFilter(String type){ //构造函数
        this.type = type;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(type);
    }
}
