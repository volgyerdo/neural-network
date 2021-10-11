/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volgyerdo.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class ZipTest {

    public static void main(String[] args) throws IOException {
        one();
        two();
    }

    private static void one() throws IOException{
        String s = "Orchis, genus of about 20 species of terrestrial orchids (family Orchidaceae) native to Eurasia and northern Africa. The tuberous roots of the early purple orchid (Orchis mascula) and several other species contain a nutritive starch. In southern Europe they are collected and dried to produce a flour that is mixed with sugar, flavourings, and liquid (such as water or milk) to produce a drink called salep. Orchis species are characterized by a pair of egg-shaped underground tubers. Each plant bears a single flower spike with many purple, pink, or white flowers, and most species have several narrow leaves at the base. The petals and sepals often form a helmetlike structure, and the flower lip usually has several lobes. The green-winged orchid (O. morio) is widely distributed throughout Eurasia. The monkey orchid (O. simia), the man orchid (O. anthropophora), the soldier, or military, orchid (O. militaris), and the naked man orchid (O. italica) all have flowers that resemble helmeted human figures. (See also man orchid.) Other Eurasian species of Orchis include some known as marsh orchids and others known as spotted orchids.";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try ( GZIPOutputStream zipStream = new GZIPOutputStream(bos);  
                ObjectOutputStream objectStream = new ObjectOutputStream(zipStream)) {
            objectStream.writeObject(s);
        }
        System.out.println((s.length() * 8) + " > " + (bos.toByteArray().length * 8));
    }
    
    private static void two() throws IOException{
        String s = "Orchis, genus of about 20 species of terrestrial orchids (family Orchidaceae) native to Eurasia and northern Africa. The tuberous roots of the early purple orchid (Orchis mascula) and several other species contain a nutritive starch. In southern Europe they are collected and dried to produce a flour that is mixed with sugar, flavourings, and liquid (such as water or milk) to produce a drink called salep. Orchis species are characterized by a pair of egg-shaped underground tubers. Each plant bears a single flower spike with many purple, pink, or white flowers, and most species have several narrow leaves at the base. The petals and sepals often form a helmetlike structure, and the flower lip usually has several lobes. The green-winged orchid (O. morio) is widely distributed throughout Eurasia. The monkey orchid (O. simia), the man orchid (O. anthropophora), the soldier, or military, orchid (O. militaris), and the naked man orchid (O. italica) all have flowers that resemble helmeted human figures. (See also man orchid.) Other Eurasian species of Orchis include some known as marsh orchids and others known as spotted orchids.";
        byte[] bytes = s.getBytes();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try ( GZIPOutputStream zipStream = new GZIPOutputStream(bos);) {
            zipStream.write(bytes, 0, bytes.length);
        }
        System.out.println((bytes.length * 8) + " > " + (bos.toByteArray().length * 8));
    }
}
