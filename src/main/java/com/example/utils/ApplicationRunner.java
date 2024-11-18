/*
package com.example.utils;

import com.example.utils.GabiaDnsUpdater;
import com.example.utils.NgrokManager;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner {

    private final NgrokManager ngrokManager;
    private final GabiaDnsUpdater dnsUpdater;

    public ApplicationRunner(NgrokManager ngrokManager, GabiaDnsUpdater dnsUpdater) {
        this.ngrokManager = ngrokManager;
        this.dnsUpdater = dnsUpdater;
    }

    @PostConstruct
    public void init() {
        String ngrokUrl = ngrokManager.getNgrokUrl();
        dnsUpdater.updateDnsRecord(ngrokUrl);
        System.out.println("Updated Gabia DNS with ngrok URL: " + ngrokUrl);
    }
}
*/
//가비아 사용할때 만들었던것 쓸모 없으면 삭제 요망