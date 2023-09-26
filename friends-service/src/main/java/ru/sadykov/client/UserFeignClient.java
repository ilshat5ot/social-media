package ru.sadykov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${user-client.name}", url = "${user-client.baseUrl}")
public interface UserFeignClient {

    @GetMapping("/{userId}")
    boolean existUser(@PathVariable Long userId);
}
