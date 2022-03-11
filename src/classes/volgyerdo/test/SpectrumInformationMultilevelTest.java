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
public class SpectrumInformationMultilevelTest {

    private static DecimalFormat format = new DecimalFormat("0.0000");

    public static void main(String[] args) {

        information("Random DNS", "cagtttctagctatattagcgggcacgactccactgcgcctatgcggaagcttgatcaaattttgaccagatcttaggtaacctgaacaagtcagttcgtaggcgtcgattggccgacgggtgcgaagaaaaaagtgatcgttgtccaacatctctagtacccaccgttgtgatgtacgttatacggacacgagcatatt");

        information("Random DNS (BINARY)", "1000011111111011000110110011001111000110010101100010010010111010001011011001101011001101100101000001101111010011100000001111111101001010000100111011110001011100001010110100001000000111100001111110011100010110011110010011110101101001001001010111011001000001000000000000011101001110011111011110100000100011101110110001110010101000101001111101110100110111001001111100110010010100100010010001100011001111");

        information("Cyclic DNS", "agctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagctagct");

        information("Cyclic DNS (BINARY)", "0001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011000110110001101100011011");

    }

    private static void information(String note, String value) {
        System.out.println(note + ": " + format.format(SpectrumInformation.information(value)));
    }

}
