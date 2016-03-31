import crdt.Counters.PNCounter;
import crdt.Graph.TwoPTwoPGraph;
import crdt.Graph.Vertex;
import crdt.sets.GSet;
import crdt.sets.TwoPhaseSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/***
 create new CRDT counter type
 increment it in a loop, print out to console.

 */
public class main {

    public static void main(String[] args){

        main main1 = new main();
        //main1.gSetTest();
        //main1.testingPNCounter();
        //main1.testingTwoPhaseSet();
        main1.testingGraph();
    }

    /*
    A method for testing the expected output of a PNCounter.
     */
    public void gSetTest()
    {
        GSet<String> gSet = new GSet<String>();
        gSet.add("a");
        gSet.add("a");

        System.out.println(gSet.get());
    }

    public void testingPNCounter()
    {
        PNCounter<String> replica1 = new PNCounter<String>();
        PNCounter<String> replica2 = new PNCounter<String>();

        replica1.increment("hostname1");System.out.println("Replica1 value =" + replica1.value());
        replica1.increment("hostname1");System.out.println("Replica1 value =" + replica1.value());


        replica2.increment("hostname2");System.out.println("\nReplica2 value =" + replica2.value());
        replica2.increment("hostname2");System.out.println("Replica2 value =" + replica2.value());

        replica1.merge( replica2 );
        System.out.println("\nReplica1 value after merge = " + replica1.value());
    }

    /*
    A method for testing the expected output of a TwoPhaseSet.
     */
    public void testingTwoPhaseSet()
    {
        TwoPhaseSet<String> replica1 = new TwoPhaseSet<String>();
        TwoPhaseSet<String> replica2 = new TwoPhaseSet<String>();

        replica1.add("a");
        replica1.add("b");
        replica1.add("c");

        replica1.merge(replica2);
        replica2.merge(replica1);

        System.out.println("**********First**********");

        System.out.println("replica1 elements = " + replica1.getSetMinus());
        System.out.println("replica2 elements = " + replica2.getSetMinus());


        replica1.remove("b");

        replica2.add("d");
        replica2.add("e");

        replica2.remove("d");

        replica1.merge(replica2);
        replica2.merge(replica1);
        System.out.println("/n**********Second**********");
        System.out.println("replica1 elements = " + replica1.getSetMinus());
        System.out.println("replica2 elements = " + replica2.getSetMinus());
    }

    public void testingGraph()
    {
        TwoPTwoPGraph<String> replica1 = new TwoPTwoPGraph<String>();
        TwoPTwoPGraph<String> replica2 = new TwoPTwoPGraph<String>();

        System.out.println("Vertices of replica 1 added: " + replica1.vertices.added.get());
        System.out.println("Hashcodes of replica 1 added: " + replica1.vertices.added.getElement(0).toString().hashCode() + " second : " + replica1.vertices.added.getElement(1).toString().hashCode());

        System.out.println("\nVertices of replica 2 added: " + replica2.vertices.added.get());
        System.out.println("Hashcodes of replica 2 added: " + replica2.vertices.added.getElement(0).toString().hashCode() + " second : " + replica2.vertices.added.getElement(1).toString().hashCode());

        System.out.println("\nare they equal?**************: " + replica1.getStartSentinel().equals(replica2.getStartSentinel()) + "\n");

        System.out.println(replica1.getStartSentinel().toString().hashCode());
        System.out.println(replica2.getStartSentinel().toString().hashCode());
        System.out.println(replica1.getStartSentinel().toString());
        System.out.println(replica2.getStartSentinel().toString());
        System.out.println(replica2.getStartSentinel().hashCode());
        System.out.println(replica2.getStartSentinel().hashCode());




        replica1.merge(replica2);
        System.out.println("\nAfter merge: added and removed sets:");
        System.out.println("Vertices: " + replica1.getGraph().vertices.added.get());
        System.out.println("Edges: " + replica1.getGraph().edges.added.get());
        System.out.println("\n");

        Vertex v = new Vertex("v");
        replica1.addBetweenVertex(replica1.getStartSentinel(), v, replica1.getEndSentinel());
        System.out.println((replica1.addBetweenVertex(replica1.getStartSentinel(), v, replica1.getEndSentinel())));

        System.out.println(replica1.getGraph().vertices.added.get());
        System.out.println(replica1.getGraph().edges.added.get());

    }
}
