package y2021.roundA.LShapedPlots;

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

import java.util.*;
import java.util.zip.ZipFile;
import java.io.*;

public class Solution {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(getIs(Solution.class, args))));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        long start=System.currentTimeMillis();
        for (int i = 1; i <= t; i++) {
            int r = in.nextInt();
            int c = in.nextInt();
            
            int[][] field = new int[r][c];
            
            for(int y=0; y<r; y++)
                for(int x=0; x<c; x++) 
                    field[y][x] = in.nextInt();
            
//            for(int[] row: field) {
//                for(int cell: row)
//                    System.err.print(cell==1?"X":".");
//                System.err.println();
//            }
            
            int count = 0;
            for(int y=0; y<r; y++) {
                for(int x=0; x<c; x++)
                    count += countLShapesMatchingCriteria(field, x, y, r, c);
            }
            
            System.out.printf("Case #%d: %d\n", i, count);
        }
        System.err.printf("\ntook: %d ms\n", System.currentTimeMillis()-start);
    }
    
    /*
     * valid L-shape has two sides
     * shorter side must have at least length 2
     * longer side must be twice as long as shorter side
     * 
     * --> shorter side must have minimum length of 2
     * --> longer side must be twice the length of the shorter side
     * --> number of possible L-shapes = length of shorter side -1
     */
    private static int countLShapesMatchingCriteria(int[][] field, int xc, int yc, int r, int c) {
        if(field[yc][xc] == 0)
            return 0;
        
        int left=1, right=1, up=1, down=1;
        int x, y;
        
        for(y=yc-1; y>=0; y--)
            if(field[y][xc] == 0)
                break;
            else
                up++;
        
        for(y=yc+1; y<r; y++)
            if(field[y][xc] == 0)
                break;
            else
                down++;
        
        for(x=xc-1; x>=0; x--)
            if(field[yc][x] == 0)
                break;
            else
                left++;
        
        for(x=xc+1; x<c; x++)
            if(field[yc][x] == 0)
                break;
            else
                right++;
            
//        System.err.print(String.format("yc: %d, xc: %d --> up: %d, right: %d, down: %d, left: %d", yc, xc, up, right, down, left));
//        System.err.println(String.format(" --> count: %d", helper(up, right) + helper(right, down) + helper(down, left) + helper(left, up)));
        return helper(up, right) + helper(right, down) + helper(down, left) + helper(left, up);
    }
    
    // see rules from above
    private static int helper(int a, int b) {
        int ret=0;
        for(int i=2; i<=a; i++)
            if(i*2<=b)
                ret++;
        for(int i=2; i<=b; i++)
            if(i*2<=a)
                ret++;
        return ret;
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