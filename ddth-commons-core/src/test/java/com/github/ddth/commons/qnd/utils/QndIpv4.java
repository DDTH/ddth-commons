package com.github.ddth.commons.qnd.utils;

import com.github.ddth.commons.utils.Ipv4Utils;

public class QndIpv4 {
    public static void main(String[] args) {
        String ip = "192.168.1.5";
        String subnet = "192.168.1.0/29";
        System.out.println(Ipv4Utils.isBelongToSubnet(ip, subnet));
    }
}
