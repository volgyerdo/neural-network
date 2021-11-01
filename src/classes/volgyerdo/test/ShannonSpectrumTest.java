/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volgyerdo.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import volgyerdo.commons.stat.ShannonEntropy;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class ShannonSpectrumTest {

    private static DecimalFormat format = new DecimalFormat("0.######");

    public static void main(String[] args) {
        String s;

        spectrum("Random DNA", "cagtttctagctatattagcgggcacgactccactgcgcctatgcggaagcttgatcaaattttgaccagatcttaggtaacctgaacaagtcagttcgtaggcgtcgattggccgacgggtgcgaagaaaaaagtgatcgttgtccaacatctctagtacccaccgttgtgatgtacgttatacggacacgagcatatt");

        spectrum("Covid DNA", "cggcagtgaggacaatcagacaactactattcaaacaattgttgaggttcaacctcaattagagatggaacttacaccag ttgttcagactattgaagtgaatagttttagtggttatttaaaacttactgacaatgtatacattaaaaatgcagacattgtggaagaagctaaaaaggtaaaaccaacagtggttgtt");

        spectrum("Cyclic DNA", "agctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagct");

        spectrum("ax50, bx50, cx50, dx50", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddd");

//        spectrum("Random string (0-9, a-z, A-Z)", "EK8Pi5sv2npTfzoaMNp87QtT5kbIUQkTJzHwICCstSmg4aksHTMwztgHFg3j8AoIobN3FycCLidGeyROiNyG5itB9kxyez1LZjFFHIBjipE7hidZyiJmilXM0mwnxzlzWSfQ0xP1OuFpWosMwS1cjYt4nyv4ONx1FceWkAf8SdvDGZVzeVzq2EmOqRF6Im2iudcYRswj");
//
//        spectrum("English string", "It might not be the first thing you notice when visiting Calvert Vaux Park, where Brooklyn meets Coney Island. But stand with your back to the highway and your nose to the public housing blocks across");
//        spectrum("Abab string", "abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababab");
//        
//        spectrum("Aaaabbbb", "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb");
//    
//        spectrum("Random string (0-9)", "24257654078714200623643448313432254477870610024014307035972608023426290238273732780753257233876696840072858968879482443441384289419539851524155496124810234577316036442045973260502775518262409753112937"); 
//        
//        spectrum("Random string (0-9, 100x2)", "24257654078714200623643448313432254477870610024014307035972608023426290238273732780753257233876696842425765407871420062364344831343225447787061002401430703597260802342629023827373278075325723387669684");
//        
//        spectrum("Random string (0-9: 100, ab: 100)", "2425765407871420062364344831343225447787061002401430703597260802342629023827373278075325723387669684abababababababababababababababababababababababababababababababababababababababababababababababababab");
    }

    private static void spectrum(String note, String s) {
        System.out.println("\n\n" + note + " (" + s.length() + "): " + s);
        for (int i = 1; i <= s.length(); i++) {
            System.out.println(format.format(ShannonEntropy.entropy(breakApart(s, i))));
        }
    }

    public static String[] breakApart(String text, int size) {
        List<String> ret = new ArrayList<>((text.length() + size - 1) / size);
        for (int start = 0; start < text.length(); start += size) {
            String subString = text.substring(start, Math.min(text.length(), start + size));
            ret.add(subString);
        }
        String[] array = ret.toArray(new String[ret.size()]);
        return array;
    }

}
