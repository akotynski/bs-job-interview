package pl.aleksanderkotbury.bs.stooq.client;

enum StooqTicker {
    WIG("WIG", "aq_wig_c2"),
    WIG20("WIG20", "aq_wig20_c2"),
    WIG20_FUT("WIG20 Fut", "aq_fw20_c0"),
    mWIG40("mWIG40", "aq_mwig40_c2"),
    sWIG80("sWIG80", "aq_swig80_c2");

    private final String name;
    private final String domId;

    StooqTicker(String name, String domId) {
        this.name = name;
        this.domId = domId;
    }

    String getName() {
        return name;
    }

    String getDomId() {
        return domId;
    }
}
