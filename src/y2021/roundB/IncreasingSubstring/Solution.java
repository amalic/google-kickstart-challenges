package y2021.roundB.IncreasingSubstring;

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
import java.io.*;
import java.util.zip.*;

public class Solution {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(getIs(Solution.class, args))));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        long start=System.currentTimeMillis();
        for (int i = 1; i <= t; i++) {
            @SuppressWarnings("unused")
            int n = in.nextInt();
            System.out.printf("Case #%d:", i);
            String str = in.next();
            char prev = str.charAt(0);
            int count = 1;
            for(char cur : str.toCharArray()) {
                if(cur>prev)
                    count++;
                else
                    count=1;
                System.out.printf(" %d", count);
                prev = cur;
            }
            System.out.println();
        }
        System.err.printf("\ntook: %d ms\n", System.currentTimeMillis()-start);
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