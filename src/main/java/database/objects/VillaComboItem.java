
package database.objects;

public class VillaComboItem {
    private Integer villaID;
    private String tierID;
    private String displayText;
    
    public VillaComboItem(Integer villaID, String tierID) {
        this.villaID = villaID;
        this.tierID = tierID;
        this.displayText = villaID + " (" + tierID + ")";
    }
    
    public Integer getVillaID() {
        return villaID;
    }
    
    public String getTierID() {
        return tierID;
    }
    
    @Override
    public String toString() {
        return displayText;
    }
}