package ru.sadykov.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@ConfigurationPropertiesScan
@PropertySource("classpath:Photo.properties")
@ConfigurationProperties(prefix = "photo")
public record PhotoProperties(List<Integer> widths, List<String> extension) {

}
