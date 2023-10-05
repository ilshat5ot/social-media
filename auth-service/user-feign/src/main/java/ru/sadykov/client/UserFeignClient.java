package ru.sadykov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${user-feign-client.name}", url = "${user-feign-client.baseUrl}")
public interface UserFeignClient {

    @GetMapping("/{userId}")
    boolean existUser(@PathVariable Long userId);
}
