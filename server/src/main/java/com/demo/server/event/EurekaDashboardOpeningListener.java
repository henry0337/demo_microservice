package com.demo.server.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Lắng nghe sự kiện khởi động ứng dụng để tự động mở giao diện Eureka Dashboard.
 * <p>
 * Khi ứng dụng Spring Boot khởi động xong ({@link ApplicationReadyEvent}), lớp này mở trình duyệt mặc định
 * và truy cập Eureka Dashboard tại URL {@code http://localhost:<port>}, với cổng được lấy từ thuộc tính {@code server.port}.
 *
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 * @see ApplicationListener
 * @see ApplicationReadyEvent
 */
@Component
public class EurekaDashboardOpeningListener implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${server.port}")
    private String port;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        final String EUREKA_SERVER_URL = "http://localhost:" + port;
        final String os = System.getProperty("os.name").toLowerCase();

        try {
            final var builder = switch (os) {
                case String s when s.contains("win") -> new ProcessBuilder("cmd", "/c", "start", EUREKA_SERVER_URL);
                case String s when s.contains("mac") -> new ProcessBuilder("cmd", "/c", "start", EUREKA_SERVER_URL);
                case String s when (s.contains("nix") || s.contains("nux")) ->
                        new ProcessBuilder("cmd", "/c", "start", EUREKA_SERVER_URL);
                default -> null;
            };

            assert builder != null;
            builder.start();
        } catch (IOException ignored) { }
    }
}
