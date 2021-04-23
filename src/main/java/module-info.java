module org.biblioteket {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.biblioteket to javafx.fxml;
    exports org.biblioteket;
}
