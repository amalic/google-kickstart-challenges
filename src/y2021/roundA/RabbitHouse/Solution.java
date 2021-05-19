package y2021.roundA.RabbitHouse;
/**
* The MIT License (MIT)
* 
* Copyright (c) 2021 - Alexander Malic
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Solution {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(getIs(Solution.class, args))));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        long start=System.currentTimeMillis();
        for (int i = 1; i <= t; i++) {
            int h = in.nextInt();
            int w = in.nextInt();
            
            long[][] grid = new long[h][w];
            List<Integer> toProcess = new ArrayList<>();
            long height, maxHeight=-1L;
            for(int y=0; y<h; y++)
                for(int x=0; x<w; x++) {
                    height = in.nextLong();
                    if(height>maxHeight) {
                        toProcess.clear();
                        toProcess.add(toIndex(w, x, y));
                        maxHeight=height;
                    } else if(height==maxHeight) {
                        toProcess.add(toIndex(w, x, y));
                    }
                    grid[y][x] = height;
                }
            
            long count = 0;
            while(!toProcess.isEmpty()) {
                List<Integer> doNext = new ArrayList<>();
                for(int index: toProcess) {
                    int x = toX(index, w);
                    int y = toY(index, w);
                    height = grid[y][x];
                    count += helper(grid, height, doNext, x, y-1)
                        + helper(grid, height, doNext, x, y+1)
                        + helper(grid, height, doNext, x-1, y)
                        + helper(grid, height, doNext, x+1, y);
                }
                toProcess.clear();
                if(!doNext.isEmpty())
                    toProcess.addAll(doNext);
                else
                    maxHeight = doubleCheck(grid, maxHeight, toProcess);
            }
            
            System.out.println(String.format("Case #%d: %d", i, count));
        }
        System.err.println(String.format("\ntook: %d ms", System.currentTimeMillis()-start));
    }

    // let's try finding the highest element that has neighbouring elements with heightDifference > 1
    private static long doubleCheck(long[][] grid, long maxHeight, List<Integer> toProcess) {
        int h=grid.length;
        int w=grid[0].length;
        long newMaxHeight = -1L;
        for(int y=0; y<h; y++)
            for(int x=0; x<w; x++) {
                long height = grid[y][x];
                if(height<maxHeight 
                    && height>=newMaxHeight
                    && ((y>0 && height-grid[y-1][x]>1) 
                        || (y<h-1 && height-grid[y+1][x]>1)
                        || (x>0 && height-grid[y][x-1]>1)
                        || (x<w-1 && height-grid[y][x+1]>1)
                )) {
                    if(height>newMaxHeight) {
                        toProcess.clear();
                        newMaxHeight=height;
                    }
                    toProcess.add(toIndex(grid[0].length, x, y));
                }
            }
        return newMaxHeight;
    }

    private static long helper(long[][] grid, long height, List<Integer> doNext, int x, int y) {
        long ret=0;
        if(x>=0 && y>=0 && x<grid[0].length && y<grid.length && height - grid[y][x] > 1) {
            ret = height - grid[y][x] - 1L;
            grid[y][x] = height - 1L;
            doNext.add(toIndex(grid[0].length, x, y));
        }
        return ret;
    }
    
    private static int toIndex(int w, int x, int y) {
        return (y*w + x);
    }
    
    private static int toX(int index, int w) {
        return index % w;
    }
    
    private static int toY(int index, int w) {
        return index / w;
    }
    
    private static InputStream getIs(Class<?> clazz, String[] args) {
        InputStream is = System.in;
        if(args.length==1) {
            File file = new File(String.format("src/%s/test_data.zip", clazz.getPackageName().replaceAll("[.]", "/")));
            try {
                @SuppressWarnings("resource")
                ZipFile zipfFile = new ZipFile(file);
                is = zipfFile.getInputStream(zipfFile.getEntry(args[0]));
            } catch (IOException e) {
                throw(new RuntimeException(String.format("Unable to get inputStream for entry %s in Zipfle %s", args[0], file.getAbsolutePath()), e));
            }
        }
        return is;
    }
    
}