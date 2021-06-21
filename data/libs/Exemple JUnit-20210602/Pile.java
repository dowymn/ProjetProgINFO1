package util;
import java.util.ArrayList;
public class Pile {
  ArrayList<Object> contenu;
  public Pile() { contenu=new ArrayList<Object>(); }
  public void empiler (Object o) { contenu.add(o); }
  public Object depiler() { return contenu.remove(contenu.size()-1); }
  public Object sommet() { return contenu.isEmpty()?null:contenu.get(contenu.size()-1); }
  public boolean vide() { return contenu.isEmpty(); }
}
