/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volgyerdo.test;

import volgyerdo.commons.stat.CompressionInformation;
import volgyerdo.commons.stat.ShannonInformation;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class InformationTest {

    public static void main(String[] args) {
        String s;
        
        s = "abcdefghijklmnop";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        s = "Hello, World!";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        s = "hello world";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        s = "123123123123";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        s = "aaaa";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        s = "vdsf4gfdbőhfdtzurődbhsdh678nméáüő20ac,áűüöoghgt4328öójgbewfdsa";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        String[] array = new String[]{"vdsf4", "gfdbőh", "fdt", "zurő", "dbhsdh6", "78nméáüő20", "ac,áűüöog", "hgt43", "28", "öójg", "bewfdsa"};
        double sInfo = 0;
        double cInfo = 0;
        for(String str : array){
            sInfo += ShannonInformation.information(str);
            cInfo += CompressionInformation.information(str);
        }
        System.out.println("Array1" + " (Shannon): " + sInfo);
        System.out.println("Array2" + " (Compression): " + cInfo);
        
        s = "vdsf4gfdbőhfdtzurődbhsdh678nméáüő20ac,áűüöoghgt4328öójgbewfdsa";
        String str = "";
        for(int i = 0; i < s.length(); i++){
            str = str + String.format("%8s", Integer.toBinaryString(s.charAt(i) & 0xFF)).replace(' ', '0');
        }
        System.out.println(str + " (Shannon): " + ShannonInformation.information(str));
        System.out.println(str + " (Compression): " + CompressionInformation.information(str));
        
        s = "ababababababababababababababababababababababababababababababab";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        s = "11111111111111111111111000000000000000000000000000000000000000";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        s = "znTEcarbCXUll8O7vQwiTP5kdGM6bYDWmQK7ze40i9cbglnI6KIgsOM4Bndmsp";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        s = "Répa, retek mogyoró, korán reggel ritkán rikkant a rigó!";
        System.out.println(s + " (Shannon): " + ShannonInformation.information(s));
        System.out.println(s + " (Compression): " + CompressionInformation.information(s));
        
        s = "In the near-equilibrium regime, with the input of B maintained at a reduced level, the\n" +
"homogeneous steady state condition is stable. Equations i) and iv) predominate such that A →\n" +
"X → E appears as the primary global reaction. At low concentrations of B the overall reaction B\n" +
"→ D remains in the near equilibrium regime, hence the autocatalytic steps, equations ii) and iii),\n" +
"remain insignificant. Any tendency for inhomogeneities to develop due to steps ii) and iii) is\n" +
"damped by the random motion of the molecules, appearing as a dissipative process at the\n" +
"macromolecular level.\n" +
"However, as the concentration of B is increased, that is, as the reaction B → D moves far\n" +
"from equilibrium, a threshold is reached where equations ii) and iii) become significant. The\n" +
"homogeneous steady state maintained by random motion becomes superseded, wherein, the\n" +
"system begins to exhibit coherent temporal ordering, i.e., concentrations of X and Y at the\n" +
"microlevel coherently oscillate periodically with respect to each other. Due to molecular\n" +
"diffusion, this temporal ordering of chemical composition becomes manifest as spatial ordering in\n" +
"the reaction volume, wherein shells of alternating X, Y concentration expand outward from the\n" +
"location of the initial instability, invading the surrounding homogeneous medium with a\n" +
"spherically symmetric periodic structure. These shells may themselves be static or propagating.*\n" +
"The oscillating reaction system has at the microlevel two degrees of freedom: a state of either\n" +
"more of X and less of Y, or a state of more of Y and less of X. This inherent dichotomy becomes\n" +
"magnified and expressed as coherent behavior at the multi-molecular macro level due to the\n" +
"presence of the autocatalytic step ii) and diffusion.\n" +
"Unlike the convection example, the existence of circular causality is here not alone sufficient\n" +
"to manifest ordering. This is because the circular causal process here takes place at a microlevel\n" +
"uniformly throughout the reaction volume. It is not until this circular causality becomes\n" +
"integrated via diffusion and step ii) to produce coherent oscillations that it is able to become\n" +
"manifest as spatial ordering at the multi molecular level. Hence, a universal criterion for the\n" +
"emergence of order in either type of open system is the emergence of macrolevel circular causal\n" +
"behavior, i.e., coherent circular causal behavior at the microlevel dominating the tendency toward\n" +
"homogeneity. the former as the latter ( in that sequence) since the former has a higher negentropy, i.e., greater\n" +
"order. In actuality, the reverse is true; the individual has to expend more energy to digest the\n" +
"steak, than the hamburger; and the nutrient broth, which can be absorbed directly in the stomach\n" +
"with minimal digestion, places the least burden on the organism. Since it is calories and not\n" +
"entropy which sustains the organism, one would be wiser to choose the soup.\n" +
"Another problem with equation (1) is that it combines elements of both structure and\n" +
"process, –d e S being the import of a given quantity of structural negentropy and d i S being the\n" +
"entropy change due to irreversible processes in the system. While it is thermodynamically\n" +
"legitimate to add these quantities, from a conceptual point of view, it is like trying to add apples\n" +
"and oranges. In the end you are more confused than ever as to how open systems are able to\n" +
"form ordered structures in a spontaneous manner.\n" +
"The basic question remains unanswered. How does negentropy naturally arise when all\n" +
"spontaneous physical and chemical processes are dissipative, i.e., characterized by entropy\n" +
"increase. A system such as a cell is able to assemble macromolecules of immense complexity\n" +
"creating an ordered macrolevel structure. But, at the microlevel, all the chemical processes\n" +
"involved in this anabolic process are dissipative. While the mechanism of protein synthesis is\n" +
"fairly well understood, the question remains; how did the phenomenon of protein synthesis first\n" +
"arise? Who taught the cell this trick of generating negentropy using common every day positive\n" +
"entropic processes? To avoid the pitfall of vitalism, we must conclude that this phenomenon\n" +
"evolved from simple prebiotic ordering principles, and that in the course of evolution, has become\n" +
"manifest in the preprogrammed and highly complex processes of the cell.\n" +
"Hence, the spontaneous emergence of order at the molecular level must be a property which\n" +
"is characteristic of simple open systems. Consequently, to come to an understanding of how\n" +
"negentropy arises in open systems, it is best to study simple examples such as the emergence of\n" +
"order in thermal convection and in nonlinear chemical reaction systems.\n" +
"First, though, I will state some general laws relating to process and structural order.\n" +
"1) All elemental processes are dispersive (dissipative).\n" +
"2) Physical order, \"negentropy\" manifests at a macroscopic level when a macrolevel dispersive\n" +
"process having many degrees of freedom is intersected and dominated by a macrolevel\n" +
"dispersive process having two degrees of freedom. Related to this:\n" +
"Order is the emergent expression of a cyclically causal phenomenon, i.e., of self-referential\n" +
"causality.\n" +
"In the case of thermal convection, such as that produced in a pan of water heated on a stove,\n" +
"there are two elemental dissipative processes involved: a) vertical thermal convective dissipation,\n" +
"and b) non-directional spatial dissipation of ordered molecular states. In the near equilibrium\n" +
"regime, the homogeneous steady state condition is stable. Heat is dissipated upward via thermal\n" +
"conduction. Any symmetry-breaking fluctuations, such as the formation of local pockets of\n" +
"water at higher or lower densities, are damped by the random motion of the molecules., i.e.,\n" +
"process b) dominates process a).\n" +
"As the thermal gradient is increased, i.e., as the system is moved further from equilibrium, a\n" +
"threshold is reached beyond which the symmetry of the system is broken and where thermal\n" +
"convection emerges as the dominant mechanism of dissipation, i.e., process a) supercedes process\n" +
"b). The transition from conduction to convection is marked by increased thermal dissipation.\n" +
"Hence, in this particular example the change from one mode to the other is itself governed by the\n" +
"dispersion principle.";
        System.out.println("Log English text" + " (Shannon): " + ShannonInformation.information(s));
        System.out.println("Log English text" + " (Compression): " + CompressionInformation.information(s));
        
    }

}
