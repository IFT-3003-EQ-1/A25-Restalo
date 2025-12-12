package ca.ulaval.glo2003.domain.dtos.restaurant;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MenuDto {

    @Nullable
    public String title;

    public String startDate;

    public List<MenuItemDto> items;

    public MenuDto() {
        items = new ArrayList<>();
    }
}
