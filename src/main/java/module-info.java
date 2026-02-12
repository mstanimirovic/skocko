module org.stanimirovic.skocko {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    exports org.stanimirovic.skocko;
    exports org.stanimirovic.skocko.ui;
    exports org.stanimirovic.skocko.domain;

    opens org.stanimirovic.skocko.ui to javafx.fxml;
}
