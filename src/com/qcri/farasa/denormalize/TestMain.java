/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcri.farasa.denormalize;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author kareemdarwish
 */
public class TestMain
{
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException
    {

        int i = 0;
        String arg;
        String infile = "";
        String outfile = "";
        Boolean tokenize = false;
        int args_flag = 0;

        BufferedReader br;
        BufferedWriter bw;

        while (i < args.length)
        {
            arg = args[i++];
            // 
            if (arg.equals("--help") || arg.equals("-h") || (args.length != 0 && args.length != 2 && args.length != 4 && args.length != 6))
            {
                System.out.println("Usage: java -jar DenormalizeArabic.jar <--help|-h> <[-t|--tokenize] true|false> <[-i|--input] [in-filename]> <[-o|--output] [out-filename]>");
                System.exit(-1);
            }

            if (arg.equals("--input") || arg.equals("-i"))
            {
                args_flag++;
                infile = args[i];
            }
            if (arg.equals("--output") || arg.equals("-o"))
            {
                args_flag++;
                outfile = args[i];
            }
            if (arg.equals("--tokenize") || arg.equals("-t"))
            {
                args_flag++;
                if (args[i].toLowerCase().equals("true"))
                {
                    tokenize = true;
                }
            }
        }


        Denormalize dn = new Denormalize();

        if(!infile.equals("")) 
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(infile))));
        else 
            br = new BufferedReader(new InputStreamReader(System.in));
        if(!outfile.equals(""))
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outfile))));
        else
            bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String line   = "";
       
        while ((line = br.readLine()) != null)
            {   
                String output = "";
                ArrayList<String> words;

                if(tokenize)
                    words = ArabicUtils.tokenize(line);
                else
                    // If the text is tokenized already!
                    words = new ArrayList<String>(Arrays.asList(line.split(" ")));

                for (String w : words)
                    output += dn.correctWord(w) + " ";
                //System.err.println(output.trim());
                bw.write(output.trim()+"\n");
                bw.flush();
            }

        bw.close();
        br.close();
    }
}
    