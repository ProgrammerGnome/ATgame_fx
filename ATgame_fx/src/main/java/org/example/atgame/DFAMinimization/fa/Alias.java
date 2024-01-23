package org.example.atgame.DFAMinimization.fa;

public class Alias {
    private String name;
    private boolean enabled;

    public Alias() {
        this.name = "";
        this.enabled = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (this.enabled)
            this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
