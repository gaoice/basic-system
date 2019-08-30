package com.gaoice.system.common.netty;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NettyController {

    @RequestMapping("/test/telnetClient")
    public void send(String msg) {
        TelnetClient.writeAndFlush(msg);
    }

}
