/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volgyerdo.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import volgyerdo.commons.stat.ShannonInformation;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class NewInformationTest {

    private static DecimalFormat format = new DecimalFormat("0.0000");

    public static void main(String[] args) {
        String s;

        information("Random DNA", "cagtttctagctatattagcgggcacgactccactgcgcctatgcggaagcttgatcaaattttgaccagatcttaggtaacctgaacaagtcagttcgtaggcgtcgattggccgacgggtgcgaagaaaaaagtgatcgttgtccaacatctctagtacccaccgttgtgatgtacgttatacggacacgagcatatt");

        information("Covid DNA", "cggcagtgaggacaatcagacaactactattcaaacaattgttgaggttcaacctcaattagagatggaacttacaccagttgttcagactattgaagtgaatagttttagtggttatttaaaacttactgacaatgtatacattaaaaatgcagacattgtggaagaagctaaaaaggtaaaaccaacagtggttgtt");

        information("Cyclic DNA", "agctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagct");

        information("ax50, bx50, cx50, dx50", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddd");

        information("Random string (0-9, a-z, A-Z)", "EK8Pi5sv2npTfzoaMNp87QtT5kbIUQkTJzHwICCstSmg4aksHTMwztgHFg3j8AoIobN3FycCLidGeyROiNyG5itB9kxyez1LZjFFHIBjipE7hidZyiJmilXM0mwnxzlzWSfQ0xP1OuFpWosMwS1cjYt4nyv4ONx1FceWkAf8SdvDGZVzeVzq2EmOqRF6Im2iudcYRswj");
       
        information("English string", "It might not be the first thing you notice when visiting Calvert Vaux Park, where Brooklyn meets Coney Island. But stand with your back to the highway and your nose to the public housing blocks across");
      
        information("Abab string", "abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababab");

        information("Aaaabbbb", "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb");

        information("Random string (0-9)", "24257654078714200623643448313432254477870610024014307035972608023426290238273732780753257233876696840072858968879482443441384289419539851524155496124810234577316036442045973260502775518262409753112937");

        information("Random string (0-9, 100x2)", "24257654078714200623643448313432254477870610024014307035972608023426290238273732780753257233876696842425765407871420062364344831343225447787061002401430703597260802342629023827373278075325723387669684");

        information("Random string (0-9: 100, ab: 100)", "2425765407871420062364344831343225447787061002401430703597260802342629023827373278075325723387669684abababababababababababababababababababababababababababababababababababababababababababababababababab");
    }

    private static void information(String note, String s) {
        //System.out.println("\n-----------------------\n" + note + "\n-----------------------");
        double information = 0;
        String[] atomicParts = breakApart(s, 1);
        Set<String> atomicSet = new HashSet<>();
        for (String p : atomicParts) {
            atomicSet.add(p);
        }
        double N = s.length();
        double n = atomicSet.size();
        double absoluteMax = maxInformation(N, n, 1);
        for (int r = 1; r <= N / 2; r++) {
            String[] parts = breakApart(s, r);
            double maxInfo = maxInformation(N, n, r);
            double actualInfo = ShannonInformation.information(parts);
            actualInfo = actualInfo / maxInfo * absoluteMax;
            if (actualInfo > 0) {
                actualInfo *= (actualInfo + (s.length() - lengthOfParts(parts)) * Math.log((double) r
                        / (60.11 * Math.exp(-1.6872 * (absoluteMax / N)))))
                        / actualInfo;
            }
            System.out.println(format.format(actualInfo));
            information += actualInfo;

        }
        System.out.println("\n" + note + " (" + s.length() + "): " + (information / (N / 2)) + "\n");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static double maxInformation(double N, double n, double r) {
        if (Math.pow(n, r) <= N / r) {
            return N * Math.log(n) / Math.log(2);
        } else {
            return N * Math.log(N / r) / Math.log(2) / r;
        }
    }

    private static String[] breakApart(String text, int size) {
        List<String> ret = new ArrayList<>((text.length() + size - 1) / size);
        for (int start = 0; start < text.length(); start += size) {
            String subString = text.substring(start, Math.min(text.length(), start + size));
            if (subString.length() == size) {
//                ret.add(subString);
//            } else {
//                boolean found = false;
//                for(String s : ret){
//                    if(s.startsWith(subString)){
//                        found = true;
//                        ret.add(s);
//                        break;
//                    }
//                }
//                if(!found){
                ret.add(subString);
//                }
            }
        }
        String[] array = ret.toArray(new String[ret.size()]);
        return array;
    }

    private static int lengthOfParts(String[] parts) {
        int length = 0;
        for (String part : parts) {
            length += part.length();
        }
        return length;
    }

}
