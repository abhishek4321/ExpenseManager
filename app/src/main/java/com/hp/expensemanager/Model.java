package p000h.pkg.main;

/* renamed from: h.pkg.main.Model */
public class Model {
    String ceexpamt;
    String name;
    boolean selected;

    public Model(String name, String ceexpamt, boolean selected) {
        this.name = name;
        this.ceexpamt = ceexpamt;
        this.selected = selected;
    }

    public String getName() {
        return this.name;
    }

    public String getCeamt() {
        return this.ceexpamt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCeamt(String ceamt) {
        this.ceexpamt = ceamt;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void enableEditText() {
    }
}
