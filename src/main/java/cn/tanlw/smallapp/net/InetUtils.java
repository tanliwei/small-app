package cn.tanlw.smallapp.net;

//import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.concurrent.*;

/**
 * 获取IP
 * @author longyang.lin
 * @description
 * @create 2018年05月25日13:46
 */
//@Slf4j
public class InetUtils implements Closeable {

    public static void main(String[] args) {
        InetUtils ut = new InetUtils();
        HostInfo firstNonLoopbackHostInfo = ut.findFirstNonLoopbackHostInfo();
        System.out.println(firstNonLoopbackHostInfo.hostname);
        System.out.println(firstNonLoopbackHostInfo.ipAddress);
    }

    private final ExecutorService executorService;

    public InetUtils() {
        this.executorService = Executors
                .newSingleThreadExecutor(new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("noa-register-client");
                        thread.setDaemon(true);
                        return thread;
                    }
                });
    }

    public HostInfo findFirstNonLoopbackHostInfo() {
        InetAddress address = findFirstNonLoopbackAddress();
        if (address != null) {
            return convertAddress(address);
        }
        HostInfo hostInfo = new HostInfo();
        return hostInfo;
    }

    boolean ignoreAddress(InetAddress address) {

        if (!address.isSiteLocalAddress()) {
            //log.trace("Ignoring address: " + address.getHostAddress());
            return true;
        }

        return false;
    }

    public HostInfo convertAddress(final InetAddress address) {
        HostInfo hostInfo = new HostInfo();
        Future<String> result = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return address.getHostName();
            }
        });

        String hostname;
        try {
            hostname = result.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            //log.info("Cannot determine local hostname");
            hostname = "localhost";
        }
        hostInfo.setHostname(hostname);
        hostInfo.setIpAddress(address.getHostAddress());
        return hostInfo;
    }


    public InetAddress findFirstNonLoopbackAddress() {
        InetAddress result = null;
        try {
            int lowest = Integer.MAX_VALUE;
            int j = 0;
            for (Enumeration<NetworkInterface> nics = NetworkInterface
                    .getNetworkInterfaces(); nics.hasMoreElements(); ) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp()) {
                    //log.trace("Testing interface: " + ifc.getDisplayName());
                    if (ifc.getIndex() < lowest || result == null) {
                        lowest = ifc.getIndex();
                    } else if (result != null) {
                        continue;
                    }

                    for (Enumeration<InetAddress> addrs = ifc
                            .getInetAddresses(); addrs.hasMoreElements(); ) {
                        InetAddress address = addrs.nextElement();
                        if (address instanceof Inet4Address
                                && !address.isLoopbackAddress()
                                && !ignoreAddress(address)) {
                            //log.trace("Found non-loopback interface: " + ifc.getDisplayName());
                            result = address;
                        }
                    }
                    // @formatter:on
                }
            }
        } catch (IOException ex) {
            //log.error("Cannot get first non-loopback address", ex);
        }

        if (result != null) {
            return result;
        }

        try {
//            InetAddress.
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            //log.warn("Unable to retrieve localhost");
        }

        return null;
    }

    @Override
    public void close() throws IOException {
        executorService.shutdown();
    }


    public static class HostInfo {
        public boolean override;
        private String ipAddress;
        private String hostname;

        public HostInfo(String hostname) {
            this.hostname = hostname;
        }

        public HostInfo() {
        }

        public int getIpAddressAsInt() {
            InetAddress inetAddress = null;
            String host = this.ipAddress;
            if (host == null) {
                host = this.hostname;
            }
            try {
                inetAddress = InetAddress.getByName(host);
            } catch (final UnknownHostException e) {
                throw new IllegalArgumentException(e);
            }
            return ByteBuffer.wrap(inetAddress.getAddress()).getInt();
        }

        public boolean isOverride() {
            return override;
        }

        public void setOverride(boolean override) {
            this.override = override;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }
    }
}

