/**
 * Copyright 2009, Frederic Bregier, and individual contributors by the @author
 * tags. See the COPYRIGHT.txt in the distribution for a full listing of
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3.0 of the License, or (at your option)
 * any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF
 * site: http://www.fsf.org.
 */
package goldengate.commandexec.server;

import goldengate.common.logging.GgSlf4JLoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.logging.InternalLoggerFactory;

import ch.qos.logback.classic.Level;

/**
 * LocalExec server.
 *
 *
 */
public class LocalExecServer {

    static ExecutorService threadPool;
    static ExecutorService threadPool2;

    public static void main(String[] args) throws Exception {
        InternalLoggerFactory.setDefaultFactory(new GgSlf4JLoggerFactory(
                Level.WARN));
        threadPool = Executors.newCachedThreadPool();
        threadPool2 = Executors.newCachedThreadPool();
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(threadPool, threadPool2));

        // Configure the pipeline factory.
        bootstrap.setPipelineFactory(new LocalExecServerPipelineFactory());

        // Bind and start to accept incoming connections only on local address.
        byte[] local = {127, 0, 0, 1 };
        InetAddress addr = InetAddress.getByAddress(local);
        bootstrap.bind(new InetSocketAddress(addr, 9999));
    }
}