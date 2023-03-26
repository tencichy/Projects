package com.damiskot.InventoryAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Changes {
    private Item itemBefore;
    private Item itemAfter;

}
