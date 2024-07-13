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

    opens services to javafx.base, javafx.fxml;
    opens entities to javafx.base, javafx.fxml;
    opens org.example.jdbcjavafx to javafx.fxml;
    exports org.example.jdbcjavafx;
}