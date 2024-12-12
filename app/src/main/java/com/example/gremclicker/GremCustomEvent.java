package com.example.gremclicker;

public class GremCustomEvent {
    public interface GremObjectListener {
        public void onObjectReady();
        // or when data has been loaded
        public void onDataLoaded();
    }


    private GremObjectListener listener;

    GremCustomEvent() {
        this.listener = null;
    }
    public void setGremListener(GremObjectListener gol) {
        this.listener = gol;
    }

}
