package test;
import org.junit.*;
import static org.junit.Assert.*;
import util.Pile;

public class PileTest {

  Pile p;

 @Before()
 public void setUp() {
   p=new Pile();
 }

 @After()
 public void tearDown() {
   p=null;
 }

 @Test()
  public void testPile() {
    assertNotNull(p);
  }

  @Test()
  public void testVide() {
    assertTrue(p.vide());
    Object o=new Object();
    p.empiler(o);
    assertFalse(p.vide());
    p.depiler();
    assertTrue(p.vide());
}
  @Test()
  public void testSommet() {
    assertNull(p.sommet());
    Object o1=new Object();
    p.empiler(o1);
    assertEquals(p.sommet(),o1);
    Object o2=new Object();
    p.empiler(o2);
    assertNotEquals(p.sommet(),o1);
    assertEquals(p.sommet(),o2);
    assertEquals(p.depiler(),o2);
    assertEquals(p.sommet(),o1);
    assertEquals(p.depiler(),o1);
    assertNull(p.sommet());
}
  @Test()
  public void testSommetPileVide() {
    assertNull(p.sommet());
    }

  @Test()
  public void testSommetEmpiler() {
    Object o=new Object();
    p.empiler(o);
    assertEquals(o,p.sommet());
}

}
