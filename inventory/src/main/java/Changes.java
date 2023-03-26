public class Changes {
    private Item itemBefore;
    private Item itemAfter;

    public Changes(Item itemBefore, Item itemAfter) {
        this.itemBefore = itemBefore;
        this.itemAfter = itemAfter;
    }

    public Item getItemBefore() {
        return itemBefore;
    }

    public Item getItemAfter() {
        return itemAfter;
    }
}
