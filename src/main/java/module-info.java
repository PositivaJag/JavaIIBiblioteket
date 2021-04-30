module org.biblioteket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens org.biblioteket to javafx.fxml;
    exports org.biblioteket;
}
