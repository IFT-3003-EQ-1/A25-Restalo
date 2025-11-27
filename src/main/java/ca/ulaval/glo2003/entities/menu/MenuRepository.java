package ca.ulaval.glo2003.entities.menu;


public interface MenuRepository {

    public boolean create(Menu menu);

    public boolean get(String id);
}
