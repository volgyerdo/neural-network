/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volgyerdo.test;

import java.text.DecimalFormat;
import volgyerdo.commons.stat.SpectrumInformation;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class SpectrumInformationTest {

    private static DecimalFormat format = new DecimalFormat("0.0000");

    public static void main(String[] args) {

        information("Random string", "CGCACTAAATTCCAGCCGCCACGGGGCTAACAGCTAGGTGGCGACACGCACCCTGGTATAGAGGGGCATCAGGCGGACTAGTCTTTATCTAAGGATGTGGCGGACCGGGATAGGTTCGGGGCTCGACACATGTTATCCTCGCTACCGTGCAGGGGTAACCGAGCCGTTCATGATGTCCCGATCAGTCAGATATAGCGGACTCATGCCTCAGGCAGACGGGCTGTGAAATTTGGGATGGCGGAGCTCGCTCCAGGCGGTTTAAGAAGGCTCCCTCAAGTGGTAAGCTAGTGATACGGAGCCGGTAGAATGACCCCCGACAACCCGGGCATGCATTGGTCTTGTCTTTTATAACGATGCCCCCGGAGTCGCATCTTAATGAACTCTATTTTCCATCAAATATGCCTTACAAAATGGTATTTCCAAATGGCGTCAGACCGAAAGCGTTCCTCGCGTCCAACGATAGTTAGACACAGCTCTGTTTCAATAACGATTGGCACGCCGGGTGTAATTGAACAATCTCAGGCCACCGCTTAATAATAATAGACAGGTGTAGCCTTGGCGTGAGCATGTTCACATTCAAGGTTCGGTGTAGACGATTCTGGGGCACCAACCCTGAGGTGCGGGGTGTGCTGTACGGGTACTAACCCCTTCTATAATTTCCATGCTGGCGGGAGACTCAGTGCAGAGAACCATATGCGCCGATAGTAGGGGGTTTCGATAATCGAGAACCGTCAGTAACACTTATCGTTGGAGGTTTTAGTTAATGTGCCATGAGAGGCGGTCGACCGCCAGTCAGAGACTGCCAGCATCGCTACGTCGGAGACGATGGTAGGGGGGTTTGCCACGTGGAAGGTGACCGGTAGCTAGTCCTTCGCAAAACGTTGCGCAACCGCAACTTCGGGATATGAAACTGGCTGACAGAAGCCTACTCTCTTAGATCTGTCGATGGCGCCGCTATGGATCTCGTTCTTACCATTGCTCTAGATCCCCTGGTGCTCAG");

        information("1", "0010101111001001");

        information("2", "1010101010101010");

        information("3", "1111111100000000");

        information("Random DNS", "cagtttctagctatattagcgggcacgactccactgcgcctatgcggaagcttgatcaaattttgaccagatcttaggtaacctgaacaagtcagttcgtaggcgtcgattggccgacgggtgcgaagaaaaaagtgatcgttgtccaacatctctagtacccaccgttgtgatgtacgttatacggacacgagcatatt");

        information("Covid DNS", "cggcagtgaggacaatcagacaactactattcaaacaattgttgaggttcaacctcaattagagatggaacttacaccagttgttcagactattgaagtgaatagttttagtggttatttaaaacttactgacaatgtatacattaaaaatgcagacattgtggaagaagctaaaaaggtaaaaccaacagtggttgtta");

        information("Cyclic DNS", "agctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagct");

        information("ax50, bx50, cx50, dx50", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddd");

        information("ax100, cx100", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");

        information("50xR1, 50xR1, 50xR1, 50xR1", "cagtttctagctatattagcgggcacgactccactgcgcctatgcggaagcagtttctagctatattagcgggcacgactccactgcgcctatgcggaagcagtttctagctatattagcgggcacgactccactgcgcctatgcggaagcagtttctagctatattagcgggcacgactccactgcgcctatgcggaag");

        information("Random string (0-9, a-z, A-Z)", "EK8Pi5sv2npTfzoaMNp87QtT5kbIUQkTJzHwICCstSmg4aksHTMwztgHFg3j8AoIobN3FycCLidGeyROiNyG5itB9kxyez1LZjFFHIBjipE7hidZyiJmilXM0mwnxzlzWSfQ0xP1OuFpWosMwS1cjYt4nyv4ONx1FceWkAf8SdvDGZVzeVzq2EmOqRF6Im2iudcYRswj");

        information("English string", "It might not be the first thing you notice when visiting Calvert Vaux Park, where Brooklyn meets Coney Island. But stand with your back to the highway and your nose to the public housing blocks across");

        information("Abab string", "abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababab");

        information("Aaaabbbb", "aaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbbaaaabbbb");

        information("Random string (0-9)", "24257654078714200623643448313432254477870610024014307035972608023426290238273732780753257233876696840072858968879482443441384289419539851524155496124810234577316036442045973260502775518262409753112937");

        information("Random string (0-9, 100x2)", "24257654078714200623643448313432254477870610024014307035972608023426290238273732780753257233876696842425765407871420062364344831343225447787061002401430703597260802342629023827373278075325723387669684");

        information("Random string (0-9: 100, ab: 100)", "2425765407871420062364344831343225447787061002401430703597260802342629023827373278075325723387669684abababababababababababababababababababababababababababababababababababababababababababababababababab");

        information("Ismétlődő szövegrész", "Egy ismétlődő szövegrész ezEgy ismétlődő szövegrész ezEgy ismétlődő szövegrész ezEgy ismétlődő szövegrész ezEgy ismétlődő szövegrész ezEgy ismétlődő szövegrész ezEgy ismétlődő szövegrész ezEgy ismétlődő szövegrész ezEgy ismétlődő szövegrész ezEgy ismétlődő szövegrész ezEgy ismétlődő szövegrész ezEgy ismétlődő szövegrész ez");

        information("Összes karakter", "0123456789öüóqwertzuiopőúasdfghjkléáűíyxcvbnm,.-§'+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_¬~>*÷×$ß¤łŁ|Ä¶ŧ–€Í");

        information("Összes karakter x 3", "0123456789öüóqwertzuiopőúasdfghjkléáűíyxcvbnm,.-§'+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_¬~>*÷×$ß¤łŁ|Ä¶ŧ–€Í0123456789öüóqwertzuiopőúasdfghjkléáűíyxcvbnm,.-§'+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_¬~>*÷×$ß¤łŁ|Ä¶ŧ–€Í0123456789öüóqwertzuiopőúasdfghjkléáűíyxcvbnm,.-§'+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_¬~>*÷×$ß¤łŁ|Ä¶ŧ–€Í");

        information("1-9 x 3", "123456789123456789123456789");
    }

    private static void information(String note, String value) {
        System.out.println(note + ": " + format.format(SpectrumInformation.information(value)));
    }

}
