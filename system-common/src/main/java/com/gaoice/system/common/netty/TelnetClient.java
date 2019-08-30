/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.gaoice.system.common.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Simplistic telnet client.
 */
public final class TelnetClient {

    static final Logger LOGGER = LoggerFactory.getLogger(TelnetClient.class);

    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8992" : "8023"));

    static Bootstrap b;
    static Channel ch;
    static EventLoopGroup group;

    public static void initChannel() throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new TelnetClientInitializer(sslCtx));

            // Start the connection attempt.
            ch = b.connect(HOST, PORT).sync().channel();
        } catch (Exception e) {
            LOGGER.error("NettyClient 异常：" + e.getMessage());
        }
    }

    public static void writeAndFlush(String msg) {
        try {
            if (msg == null) {
                return;
            }
            if (ch == null) {
                TelnetClient.initChannel();
            }

            // Sends the received line to the server.
            ChannelFuture lastWriteFuture = ch.writeAndFlush(msg + "\r\n");

            // If user typed the 'bye' command, wait until the server closes
            // the connection.
            if ("bye".equals(msg.toLowerCase())) {
                ch.closeFuture().sync();
            }
            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } catch (Exception e) {
            LOGGER.error("NettyClient 异常：" + e.getMessage());
        }
    }

    public static void shutdown() {
        group.shutdownGracefully();
    }
}
