module org.example.jdbcjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.base;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens org.example.jdbcjavafx.entities to javafx.base;
    opens org.example.jdbcjavafx;
    opens org.example.jdbcjavafx.services to javafx.base, javafx.fxml;
    exports org.example.jdbcjavafx;
}